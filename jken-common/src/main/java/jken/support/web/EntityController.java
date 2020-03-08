/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-04T15:00:37.481+08:00
 */

package jken.support.web;

import com.querydsl.core.types.Predicate;
import jken.support.data.jpa.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

public abstract class EntityController<T extends Entity<I>, I extends Serializable> extends CrudController<T, I> {

    /**
     * 获取列表数据(分页)
     *
     * @param predicate
     * @param pageable
     * @return
     */
    @GetMapping(produces = "application/json")
    @ResponseBody
    public abstract Page<T> list(Predicate predicate, Pageable pageable);


    protected Page<T> doInternalPage(Predicate predicate, Pageable pageable) {
        return getService().findAll(predicate, pageable);
    }

}
