package jken.module.core.support.thymeleaf;

import jken.module.core.entity.DictItem;
import jken.module.core.service.DictService;
import jken.support.thymeleaf.ModuleExpressionObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ModuleExpressionObject(objectName = "dict")
public class DictExpressionObject {

    @Autowired
    private DictService dictService;

    public List<DictItem> items(String code) {
        return dictService.getItemsByCode(code);
    }

}
