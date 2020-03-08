/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.482+08:00
 */

package jken.module.core.repo;

import jken.module.core.entity.MenuItem;
import jken.support.data.jpa.QuerydslTreeRepository;

public interface MenuItemRepository extends QuerydslTreeRepository<MenuItem, Long> {
}
