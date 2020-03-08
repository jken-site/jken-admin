/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-05T19:35:47.642+08:00
 */

package jken.support.data.jpa;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity<I extends Serializable> extends AbstractPersistable<I> implements Entity<I> {

    @Override
    public String toString() {
        return ofString();
    }
}
