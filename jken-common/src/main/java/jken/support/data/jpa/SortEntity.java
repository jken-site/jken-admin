/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.430+08:00
 */

package jken.support.data.jpa;

import jken.support.data.Sortable;
import org.apache.commons.lang3.builder.CompareToBuilder;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class SortEntity<U, I extends Serializable> extends DataEntity<U, I> implements Sortable, Comparable<SortEntity<U, I>> {
    private Integer sortNo;

    @Override
    public Integer getSortNo() {
        return sortNo;
    }

    @Override
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    @Override
    public int compareTo(SortEntity<U, I> o) {
        return new CompareToBuilder().append(getSortNo(), o.getSortNo()).append(getId(), o.getId()).toComparison();
    }
}
