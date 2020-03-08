/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-06T17:28:25.790+08:00
 */

package jken.module.core.repo;

import com.querydsl.core.types.dsl.StringExpression;
import jken.module.core.entity.Message;
import jken.module.core.entity.QMessage;
import jken.support.data.jpa.QuerydslEntityRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface MessageRepository extends QuerydslEntityRepository<Message, Long>, QuerydslBinderCustomizer<QMessage> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QMessage qMessage) {
        querydslBindings.bind(qMessage.title).first(StringExpression::contains);
    }
}
