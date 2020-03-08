/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.434+08:00
 */

package jken.support.data.jpa;

import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface TreeRepository<T extends TreeEntity<T, ?, I>, I extends Serializable> extends EntityRepository<T, I> {

    List<T> findRoots();

    List<T> findAllChildren(T root);

    List<T> findByRoot(T root);

}
