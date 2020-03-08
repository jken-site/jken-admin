package jken.module.scheduler.support.mvc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SchedulerWebConfiguration implements WebMvcConfigurer {

    private final ApplicationContext context;

    public SchedulerWebConfiguration(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

        FormattingConversionService conversionService = (FormattingConversionService) registry;

        JobConverter<FormattingConversionService> jobConverter = new JobConverter<>(conversionService);
        jobConverter.setApplicationContext(context);

        TriggerConverter<FormattingConversionService> triggerConverter = new TriggerConverter<>(conversionService);
        triggerConverter.setApplicationContext(context);
    }
}
