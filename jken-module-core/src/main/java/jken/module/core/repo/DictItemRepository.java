/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-07T18:17:05.545+08:00
 */

package jken.module.core.repo;

import jken.module.core.entity.DictItem;
import jken.support.data.jpa.QuerydslEntityRepository;

public interface DictItemRepository extends QuerydslEntityRepository<DictItem, Long> {
}
