/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-06T17:31:00.910+08:00
 */

package jken.module.core.service;

import jken.module.core.entity.Message;
import jken.support.service.CrudService;
import org.springframework.stereotype.Service;

@Service
public class MessageService extends CrudService<Message, Long> {
}
