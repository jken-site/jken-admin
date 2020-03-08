/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T21:02:44.048+08:00
 */

package jken.module.blog.web;

import jken.module.blog.entity.Article;
import com.querydsl.core.types.Predicate;
import jken.support.web.EntityController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController extends EntityController<Article, Long> {
    @Override
    public Page<Article> list(Predicate predicate, Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }
}
