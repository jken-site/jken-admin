/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-04T21:29:01.402+08:00
 */

package jken.support.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jken.support.data.jpa.Entity;
import jken.support.json.pathfilter.AntPathFilterMixin;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JsonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return new JkenJackson2ObjectMapperBuilderCustomizer();
    }

    @Bean
    public SimpleModule simpleModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Page.class, new PageSerializer<>());
        return module;
    }

    static final class JkenJackson2ObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer, Ordered {

        @Override
        public void customize(Jackson2ObjectMapperBuilder builder) {
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            builder.featuresToDisable(SerializationFeature.FAIL_ON_SELF_REFERENCES);
            builder.mixIn(Entity.class, AntPathFilterMixin.class);
        }

        @Override
        public int getOrder() {
            return Ordered.LOWEST_PRECEDENCE;
        }
    }
}
