/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.455+08:00
 */

package jken.support.service;

import jken.support.data.jpa.QuerydslTreeRepository;
import jken.support.data.jpa.TreeEntity;

import java.io.Serializable;
import java.util.List;

public abstract class TreeService<T extends TreeEntity<T, ?, I>, I extends Serializable> extends CrudService<T, I> {

    @Override
    protected QuerydslTreeRepository<T, I> getRepository() {
        return (QuerydslTreeRepository<T, I>) super.getRepository();
    }

    public List<T> findRoots() {
        return getRepository().findRoots();
    }

    public List<T> findAllChildren(T root) {
        return getRepository().findAllChildren(root);
    }
}
