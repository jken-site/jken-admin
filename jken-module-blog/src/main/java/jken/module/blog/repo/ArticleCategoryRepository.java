/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T21:00:40.987+08:00
 */

package jken.module.blog.repo;

import jken.module.blog.entity.ArticleCategory;
import jken.support.data.jpa.QuerydslTreeRepository;

public interface ArticleCategoryRepository extends QuerydslTreeRepository<ArticleCategory, Long> {
}
