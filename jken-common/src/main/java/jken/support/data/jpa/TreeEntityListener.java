/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.433+08:00
 */

package jken.support.data.jpa;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;

public class TreeEntityListener {

    /**
     * 树路径分隔符
     */
    private static final String TREE_PATH_SEPARATOR = ",";

    @PrePersist
    public <T extends TreeEntity<T, ?, I>, I extends Serializable> void prePersist(TreeEntity<T, ?, I> entity) {
        T parent = entity.getParent();
        if (parent != null) {
            entity.setTreePath(parent.getTreePath() + parent.getId() + TREE_PATH_SEPARATOR);
        } else {
            entity.setTreePath(TREE_PATH_SEPARATOR);
        }
    }

    @PreUpdate
    public <T extends TreeEntity<T, ?, I>, I extends Serializable> void preUpdate(TreeEntity<T, ?, I> entity) {
        T parent = entity.getParent();
        if (parent != null) {
            entity.setTreePath(parent.getTreePath() + parent.getId() + TREE_PATH_SEPARATOR);
        } else {
            entity.setTreePath(TREE_PATH_SEPARATOR);
        }
    }


}
