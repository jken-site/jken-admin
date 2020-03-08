/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.481+08:00
 */

package jken.module.core.repo;

import com.querydsl.core.types.dsl.StringExpression;
import jken.module.core.entity.Corp;
import jken.module.core.entity.QCorp;
import jken.support.data.jpa.QuerydslEntityRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface CorpRepository extends QuerydslEntityRepository<Corp, Long>, QuerydslBinderCustomizer<QCorp> {

    Corp findByCode(String code);

    @Override
    default void customize(QuerydslBindings querydslBindings, QCorp qCorp) {
        querydslBindings.bind(qCorp.name, qCorp.code).first((StringExpression::contains));
    }
}
