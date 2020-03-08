/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T21:01:59.859+08:00
 */

package jken.module.blog.web;

import jken.module.blog.entity.ArticleTag;
import com.querydsl.core.types.Predicate;
import jken.support.web.EntityController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articleTag")
public class ArticleTagController extends EntityController<ArticleTag, Long> {
    @Override
    public Page<ArticleTag> list(Predicate predicate, Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }
}
