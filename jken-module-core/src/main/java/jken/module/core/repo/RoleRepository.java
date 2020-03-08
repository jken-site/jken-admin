/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.483+08:00
 */

package jken.module.core.repo;

import com.querydsl.core.types.dsl.StringExpression;
import jken.module.core.entity.QRole;
import jken.module.core.entity.Role;
import jken.support.data.jpa.QuerydslEntityRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface RoleRepository extends QuerydslEntityRepository<Role, Long>, QuerydslBinderCustomizer<QRole> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QRole qRole) {
        querydslBindings.bind(qRole.name, qRole.code).first(StringExpression::contains);
    }
}
