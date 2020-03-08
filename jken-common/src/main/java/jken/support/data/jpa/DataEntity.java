/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-05T19:35:47.645+08:00
 */

package jken.support.data.jpa;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class DataEntity<U, I extends Serializable> extends AbstractAuditable<U, I> implements Entity<I> {

    @Override
    public String toString() {
        return ofString();
    }
}