package jken.module.scheduler.support.mvc;

import jken.module.scheduler.model.TriggerModel;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class TriggerConverter<T extends ConversionService & ConverterRegistry>
        implements ConditionalGenericConverter, ApplicationContextAware {

    private final T conversionService;

    private Optional<TriggerConverter.ToTriggerKeyConverter> toTriggerKeyConverter = Optional.empty();
    private Optional<TriggerConverter.ToTriggerModelConverter> toTriggerModelConverter = Optional.empty();

    public TriggerConverter(T conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.toTriggerKeyConverter = Optional.of(new TriggerConverter.ToTriggerKeyConverter());
        this.toTriggerKeyConverter.ifPresent(it -> this.conversionService.addConverter(it));

        Scheduler scheduler = applicationContext.getBean(Scheduler.class);
        this.toTriggerModelConverter = Optional.of(new TriggerConverter.ToTriggerModelConverter(scheduler));
        this.toTriggerModelConverter.ifPresent(it -> this.conversionService.addConverter(it));
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return false;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Object.class, Object.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return getConverter(targetType).map(it -> it.convert(source, sourceType, targetType)).orElse(source);
    }

    private Optional<? extends GenericConverter> getConverter(TypeDescriptor targetType) {
        return TriggerKey.class.isAssignableFrom(targetType.getType()) ? toTriggerKeyConverter : toTriggerModelConverter;
    }

    private class ToTriggerKeyConverter implements GenericConverter {

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new ConvertiblePair(String.class, TriggerKey.class));
        }

        @Override
        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            if (source == null || !StringUtils.hasText(source.toString())) {
                return null;
            }

            String src = (String) source;
            String[] values = src.split("-");
            return TriggerKey.triggerKey(values[0], values[1]);
        }
    }

    private class ToTriggerModelConverter implements GenericConverter {

        private Scheduler scheduler;

        public ToTriggerModelConverter(Scheduler scheduler) {
            this.scheduler = scheduler;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new ConvertiblePair(String.class, TriggerModel.class));
        }

        @Override
        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            if (source == null || !StringUtils.hasText(source.toString())) {
                return null;
            }

            if (sourceType.equals(targetType)) {
                return source;
            }

            TriggerKey triggerKey = conversionService.convert(source, TriggerKey.class);
            try {
                Trigger trigger = scheduler.getTrigger(triggerKey);
                return TriggerModel.from(trigger);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
