/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.416+08:00
 */

package jken.support.data.jpa;

import jken.support.data.Corpable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class CorpableEntity<U, I extends Serializable> extends DataEntity<U, I> implements Corpable {

    @Column(length = 100)
    private String corpCode;

    @Override
    public String getCorpCode() {
        return corpCode;
    }

    @Override
    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }
}
