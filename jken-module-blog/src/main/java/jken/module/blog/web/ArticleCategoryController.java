/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T21:03:20.238+08:00
 */

package jken.module.blog.web;

import jken.module.blog.entity.ArticleCategory;
import com.querydsl.core.types.Predicate;
import jken.support.web.TreeController;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/articleCategory")
public class ArticleCategoryController extends TreeController<ArticleCategory, Long> {
    @Override
    public List<Object> list(@QuerydslPredicate(root = ArticleCategory.class) Predicate predicate, Sort sort) {
        return super.doList(predicate, sort);
    }

    @Override
    public List<Object> tree(@QuerydslPredicate(root = ArticleCategory.class) Predicate predicate, Sort sort) {
        return super.doTree(predicate, sort);
    }

    @Override
    protected void extraListConvert(Map<String, Object> data, ArticleCategory entity) {
        data.put("name", entity.getName());
    }

    @Override
    protected String treeNodeDisplay(ArticleCategory entity) {
        return entity.getName();
    }
}
