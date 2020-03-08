/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-04T21:29:01.404+08:00
 */

package jken.support.mvc;

import com.google.common.base.Strings;
import jken.support.json.pathfilter.Jackson2Helper;
import jken.support.json.pathfilter.PathFilter;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class JacksonResponseCustomizer implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return ResultBuilder.SUCCESS;
        }
        MappingJacksonValue container = getOrCreateContainer(body);
        beforeBodyWriteInternal(container, selectedContentType, returnType, request, response);
        return container;
    }

    protected MappingJacksonValue getOrCreateContainer(Object body) {
        return (body instanceof MappingJacksonValue ? (MappingJacksonValue) body : new MappingJacksonValue(body));
    }

    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        Object value = bodyContainer.getValue();

        if (value != null) {
            HttpServletRequest httpRequest = ((ServletServerHttpRequest) request).getServletRequest();
            String filterString = httpRequest.getParameter("_path_filter");
            String[] filters = Strings.isNullOrEmpty(filterString) ? null : filterString.split(",");
            if (filters == null || filters.length == 0) {
                if (returnType.hasMethodAnnotation(PathFilter.class)) {
                    filters = returnType.getMethodAnnotation(PathFilter.class).value();
                }
            }

            if (filters == null || filters.length == 0) {
                filters = new String[]{"*", "*.*"};
            }

            bodyContainer.setFilters(Jackson2Helper.buildFilterProvider(filters));
        }

        if (value instanceof Page) {
            Page<?> page = (Page<?>) value;
            bodyContainer.setValue(new ResultBuilder(0, "").layuiPage(page).build());
        } else if (value instanceof DataWrap) {
            bodyContainer.setValue(new ResultBuilder(0, "").data(((DataWrap) value).getData()).build());
        } else {
            bodyContainer.setValue(new ResultBuilder(0, "").data(value).build());
        }
    }

    static class ResultBuilder implements Builder<Map<String, Object>> {

        private static final Map<String, Object> SUCCESS = new ResultBuilder(0, "").build();

        private Map<String, Object> map = new HashMap<>();

        public ResultBuilder(Integer code, String msg) {
            put("code", code).put("msg", msg);
        }

        public ResultBuilder newResult(Integer code, String msg) {
            return put("code", code).put("msg", msg);
        }

        public ResultBuilder data(Object data) {
            return put("data", data);
        }

        public ResultBuilder put(String key, Object value) {
            map.put(key, value);
            return this;
        }

        public ResultBuilder layuiPage(Page<?> page) {
            return data(page.getContent()).put("count", page.getTotalElements());
        }

        @Override
        public Map<String, Object> build() {
            return map;
        }
    }
}
