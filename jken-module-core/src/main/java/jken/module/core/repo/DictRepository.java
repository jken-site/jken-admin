/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-07T18:16:36.534+08:00
 */

package jken.module.core.repo;

import com.querydsl.core.types.dsl.StringExpression;
import jken.module.core.entity.Dict;
import jken.module.core.entity.QDict;
import jken.support.data.jpa.QuerydslEntityRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface DictRepository extends QuerydslEntityRepository<Dict, Long>, QuerydslBinderCustomizer<QDict> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QDict qDict) {
        querydslBindings.bind(qDict.name, qDict.code).first(StringExpression::contains);
    }
}
