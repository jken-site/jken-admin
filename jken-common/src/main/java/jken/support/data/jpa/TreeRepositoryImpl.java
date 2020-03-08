/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.436+08:00
 */

package jken.support.data.jpa;

import jken.support.data.TreeHelper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class TreeRepositoryImpl<T extends TreeEntity<T, ?, I>, I extends Serializable> extends EntityRepositoryImpl<T, I> implements TreeRepository<T, I> {

    public TreeRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public List<T> findRoots() {
        return getQuery((root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("parent")), (Sort) null).getResultList();
    }

    @Override
    public List<T> findAllChildren(T parent) {
        return getQuery((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("treePath"), parent.getTreePath() + parent.getId() + "%"), (Sort) null).getResultList();
    }

    @Override
    public List<T> findByRoot(T root) {
        List<T> allChildren = root == null ? findAll() : findAllChildren(root);
        return TreeHelper.toTree(root, allChildren);
    }
}
