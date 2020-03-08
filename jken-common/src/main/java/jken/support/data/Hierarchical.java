/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.440+08:00
 */

package jken.support.data;

import java.util.List;

public interface Hierarchical<T extends Hierarchical<T>> extends Sortable {
    T getParent();

    void setParent(T parent);

    List<T> getChildren();

    void setChildren(List<T> children);
}
