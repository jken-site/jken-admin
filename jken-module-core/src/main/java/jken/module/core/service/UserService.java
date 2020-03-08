/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.490+08:00
 */

package jken.module.core.service;

import jken.module.core.entity.User;
import jken.support.service.CrudService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CrudService<User, Long> {
}
