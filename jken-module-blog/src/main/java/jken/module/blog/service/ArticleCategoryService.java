/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T21:01:31.578+08:00
 */

package jken.module.blog.service;

import jken.module.blog.entity.ArticleCategory;
import jken.support.service.TreeService;
import org.springframework.stereotype.Service;

@Service
public class ArticleCategoryService extends TreeService<ArticleCategory, Long> {
}
