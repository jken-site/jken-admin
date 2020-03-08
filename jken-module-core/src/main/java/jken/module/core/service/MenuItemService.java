/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.487+08:00
 */

package jken.module.core.service;

import jken.module.core.entity.MenuItem;
import jken.support.service.TreeService;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService extends TreeService<MenuItem, Long> {
}
