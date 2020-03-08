/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.458+08:00
 */

package jken.support.thymeleaf;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.processor.element.AbstractAttributeModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class RequiredModelProcessor extends AbstractAttributeModelProcessor {

    private static final String ATTR_NAME = "required";
    private static final int PRECEDENCE = 100;

    protected RequiredModelProcessor(String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
    }

    @Override
    protected void doProcess(ITemplateContext context, IModel model, AttributeName attributeName, String attributeValue, IElementModelStructureHandler structureHandler) {
        final IModelFactory modelFactory = context.getModelFactory();

        model.insert(1, modelFactory.createOpenElementTag("i", "style", "color: red"));
        model.insert(2, modelFactory.createText("*"));
        model.insert(3, modelFactory.createCloseElementTag("i"));
        model.insert(4, modelFactory.createText(" "));
    }
}
