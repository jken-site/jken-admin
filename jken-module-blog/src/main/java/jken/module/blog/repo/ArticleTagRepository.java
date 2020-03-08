/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T20:56:15.043+08:00
 */

package jken.module.blog.repo;

import jken.module.blog.entity.ArticleTag;
import com.querydsl.core.types.dsl.StringExpression;
import jken.module.blog.entity.QArticleTag;
import jken.support.data.jpa.QuerydslEntityRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ArticleTagRepository extends QuerydslEntityRepository<ArticleTag, Long>, QuerydslBinderCustomizer<QArticleTag> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QArticleTag qArticleTag) {
        querydslBindings.bind(qArticleTag.name).first(StringExpression::contains);
    }
}
