/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-04T15:00:37.480+08:00
 */

package jken.support.thymeleaf;

import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ModuleExpressionObjectFactory implements IExpressionObjectFactory {

    private final Map<String, Object> MODULE_OBJECTS;
    protected final Set<String> ALL_EXPRESSION_OBJECT_NAMES;

    public ModuleExpressionObjectFactory(ApplicationContext context) {
        MODULE_OBJECTS = getModuleObjects(context);
        ALL_EXPRESSION_OBJECT_NAMES = MODULE_OBJECTS.keySet();
    }

    protected Map<String, Object> getModuleObjects(ApplicationContext context) {
        Map<String, Object> beans = context.getBeansWithAnnotation(ModuleExpressionObject.class);
        Map<String, Object> result = new HashMap<>(beans.size());
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object bean = entry.getValue();
            String objectName = bean.getClass().getAnnotation(ModuleExpressionObject.class).objectName();
            result.put(objectName, bean);
        }
        return result;
    }

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return ALL_EXPRESSION_OBJECT_NAMES;
    }

    @Override
    public Object buildObject(IExpressionContext iExpressionContext, String expressionObjectName) {
        return MODULE_OBJECTS.get(expressionObjectName);
    }

    @Override
    public boolean isCacheable(final String expressionObjectName) {
        return true;
    }
}
