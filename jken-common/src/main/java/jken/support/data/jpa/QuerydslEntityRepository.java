/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.428+08:00
 */

package jken.support.data.jpa;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface QuerydslEntityRepository<T, I extends Serializable> extends EntityRepository<T, I>, QuerydslPredicateExecutor<T> {
}
