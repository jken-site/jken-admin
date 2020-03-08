/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-11T13:51:13.152+08:00
 */

package jken.support.web;

import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import jken.support.data.TreeHelper;
import jken.support.data.jpa.TreeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class TreeController<T extends TreeEntity<T, ?, I>, I extends Serializable> extends CrudController<T, I> {

    /**
     * 获取树型列表数据(分页)
     *
     * @param predicate
     * @param sort
     * @return
     */
    @GetMapping(produces = "application/json")
    @ResponseBody
    public abstract List<Object> list(Predicate predicate, Sort sort);

    @GetMapping(produces = "application/json", params = "_s=tree")
    @ResponseBody
    public abstract List<Object> tree(Predicate predicate, Sort sort);

    protected abstract void extraListConvert(Map<String, Object> data, T entity);

    protected abstract String treeNodeDisplay(T entity);

    protected List<Object> doList(Predicate predicate, Sort sort) {
        return doConvertList(predicate, sort, this::listConvert);
    }

    protected List<Object> doTree(Predicate predicate, Sort sort) {
        return treeConvert(TreeHelper.toTree(Lists.newArrayList(getService().findAll(predicate, sort))));
    }

    protected List<Object> doConvertList(Predicate predicate, Sort sort, Function<T, Object> converter) {
        return Lists.newArrayList(getService().findAll(predicate, sort)).stream().map(converter).collect(Collectors.toList());
    }

    protected Object listConvert(T entity) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", entity.getId());
        data.put("pid", entity.getParent() == null ? 0l : entity.getParent().getId());
        data.put("sortNo", entity.getSortNo());
        extraListConvert(data, entity);
        return data;
    }

    protected List<Object> treeConvert(List<T> entities) {
        if (entities == null || entities.size() == 0) {
            return null;
        }
        return entities.stream().map(entity -> {
            Map<String, Object> data = new HashMap<>();
            data.put("value", entity.getId());
            data.put("name", treeNodeDisplay(entity));
            extraTreeConvert(data, entity);
            data.put("children", treeConvert(entity.getChildren()));
            return data;
        }).collect(Collectors.toList());
    }

    protected void extraTreeConvert(Map<String, Object> data, T entity) {
    }

}
