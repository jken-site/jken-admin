/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.445+08:00
 */

package jken.support.data;

import com.google.common.base.MoreObjects;

import java.util.Comparator;

public class SortNoComparator<T extends Sortable> implements Comparator<T> {

    public static final SortNoComparator<Sortable> COMPARATOR = new SortNoComparator<>();

    @Override
    public int compare(T o1, T o2) {
        Integer sortNo1 = MoreObjects.firstNonNull(o1.getSortNo(), 0);
        Integer sortNo2 = MoreObjects.firstNonNull(o2.getSortNo(), 0);
        return sortNo1 - sortNo2;
    }

}
