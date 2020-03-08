/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.429+08:00
 */

package jken.support.data.jpa;

import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface QuerydslTreeRepository<T extends TreeEntity<T, ?, I>, I extends Serializable> extends TreeRepository<T, I>, QuerydslEntityRepository<T, I> {
}
