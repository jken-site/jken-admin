/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-05T19:35:47.646+08:00
 */

package jken.support.data.jpa;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

public interface Entity<I extends Serializable> extends Persistable<I> {

    default String ofString() {
        return getId() == null ? "" : getId().toString();
    }
}
