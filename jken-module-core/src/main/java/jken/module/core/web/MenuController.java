/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.470+08:00
 */

package jken.module.core.web;

import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;
import jken.module.core.entity.MenuItem;
import jken.support.web.TreeController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController extends TreeController<MenuItem, Long> {

    @Override
    public List<Object> list(@QuerydslPredicate(root = MenuItem.class) Predicate predicate, Sort sort) {
        return super.doList(predicate, sort);
    }

    @Override
    public List<Object> tree(@QuerydslPredicate(root = MenuItem.class) Predicate predicate, Sort sort) {
        return super.doTree(predicate, sort);
    }

    @Override
    protected void extraListConvert(Map<String, Object> data, MenuItem entity) {
        data.put("name", entity.getName());
        data.put("href", StringUtils.startsWith(entity.getHref(), "javascript:") ? "" : entity.getHref());
        data.put("code", entity.getCode());
        data.put("iconCls", Strings.nullToEmpty(entity.getIconCls()));
    }

    @Override
    protected String treeNodeDisplay(MenuItem entity) {
        return entity.getName();
    }
}
