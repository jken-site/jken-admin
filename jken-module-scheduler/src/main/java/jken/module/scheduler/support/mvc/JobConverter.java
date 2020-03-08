package jken.module.scheduler.support.mvc;

import jken.module.scheduler.model.JobModel;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
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

public class JobConverter<T extends ConversionService & ConverterRegistry>
        implements ConditionalGenericConverter, ApplicationContextAware {

    private final T conversionService;

    private Optional<JobConverter.ToJobKeyConverter> toJobKeyConverter = Optional.empty();
    private Optional<JobConverter.ToJobModelConverter> toJobModelConverter = Optional.empty();

    public JobConverter(T conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.toJobKeyConverter = Optional.of(new JobConverter.ToJobKeyConverter());
        this.toJobKeyConverter.ifPresent(it -> this.conversionService.addConverter(it));

        Scheduler scheduler = applicationContext.getBean(Scheduler.class);
        this.toJobModelConverter = Optional.of(new JobConverter.ToJobModelConverter(scheduler));
        this.toJobModelConverter.ifPresent(it -> this.conversionService.addConverter(it));
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
        return JobKey.class.isAssignableFrom(targetType.getType()) ? toJobKeyConverter : toJobModelConverter;
    }

    private class ToJobKeyConverter implements GenericConverter {

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new ConvertiblePair(String.class, JobKey.class));
        }

        @Override
        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            if (source == null || !StringUtils.hasText(source.toString())) {
                return null;
            }

            String src = (String) source;
            String[] values = src.split("-");
            return JobKey.jobKey(values[0], values[1]);
        }
    }

    private class ToJobModelConverter implements GenericConverter {

        private Scheduler scheduler;

        public ToJobModelConverter(Scheduler scheduler) {
            this.scheduler = scheduler;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new ConvertiblePair(String.class, JobModel.class));
        }

        @Override
        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            if (source == null || !StringUtils.hasText(source.toString())) {
                return null;
            }

            if (sourceType.equals(targetType)) {
                return source;
            }

            JobKey jobKey = conversionService.convert(source, JobKey.class);
            try {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                return JobModel.from(jobDetail);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
