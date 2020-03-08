/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.484+08:00
 */

package jken.module.core.repo;

import com.querydsl.core.types.dsl.StringExpression;
import jken.module.core.entity.QUser;
import jken.module.core.entity.User;
import jken.support.data.jpa.QuerydslEntityRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface UserRepository extends QuerydslEntityRepository<User, Long>, QuerydslBinderCustomizer<QUser> {

    User findByUsernameAndCorpCode(String username, String corpCode);

    @Override
    default void customize(QuerydslBindings querydslBindings, QUser qUser) {
        querydslBindings.bind(qUser.name, qUser.mail, qUser.mobile, qUser.username).first(StringExpression::contains);
        querydslBindings.excluding(qUser.password);
    }
}
