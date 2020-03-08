/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T20:59:05.874+08:00
 */

package jken.module.blog.repo;

import com.querydsl.core.types.dsl.StringExpression;
import jken.module.blog.entity.Article;
import jken.module.blog.entity.QArticle;
import jken.support.data.jpa.QuerydslEntityRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ArticleRepository extends QuerydslEntityRepository<Article, Long>, QuerydslBinderCustomizer<QArticle> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QArticle qArticle) {
        querydslBindings.bind(qArticle.title, qArticle.author).first(StringExpression::contains);
    }
}
