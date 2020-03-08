/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.426+08:00
 */

package jken.support.data.jpa;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import jken.security.CorpCodeHolder;
import jken.support.data.Corpable;
import jken.support.data.LogicDeleteable;
import jken.support.data.Sortable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

public class QuerydslEntityPredicateExecutor<T> extends QuerydslJpaPredicateExecutor<T> {

    private final JpaEntityInformation<T, ?> entityInformation;
    private final Querydsl querydsl;

    public QuerydslEntityPredicateExecutor(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager, EntityPathResolver resolver, CrudMethodMetadata metadata) {
        super(entityInformation, entityManager, resolver, metadata);

        this.entityInformation = entityInformation;
        EntityPath<T> path = resolver.createPath(entityInformation.getJavaType());
        querydsl = new Querydsl(entityManager, new PathBuilder<T>(path.getType(), path.getMetadata()));
    }

    @Override
    protected JPQLQuery<?> createQuery(Predicate... predicate) {
        JPQLQuery<?> query = super.createQuery(predicate);
        makePredicate(query);
        query = genSortable(query);
        return query;
    }

    @Override
    protected JPQLQuery<?> createCountQuery(Predicate... predicate) {
        JPQLQuery<?> query = super.createCountQuery(predicate);
        makePredicate(query);
        return query;
    }

    protected void makePredicate(JPQLQuery<?> query) {
        Class<T> domainClass = entityInformation.getJavaType();
        if (ClassUtils.isAssignable(LogicDeleteable.class, domainClass)) {
            BeanPath<T> beanPath = new BeanPath<>(domainClass, StringUtils.uncapitalize(domainClass.getSimpleName()));
            BooleanExpression deletedPropertyIsFalse = Expressions.booleanPath(PathMetadataFactory.forProperty(beanPath, "deleted"))
                    .isFalse();
            query.where(deletedPropertyIsFalse);
        }
        if (ClassUtils.isAssignable(Corpable.class, domainClass)) {
            BeanPath<T> beanPath = new BeanPath<>(domainClass, StringUtils.uncapitalize(domainClass.getSimpleName()));
            BooleanExpression equalsCurrentCorpCode = Expressions.comparablePath(String.class, PathMetadataFactory.forProperty(beanPath, "corpCode")).eq(CorpCodeHolder.getCurrentCorpCode());
            query.where(equalsCurrentCorpCode);
        }
    }

    protected JPQLQuery<?> genSortable(JPQLQuery<?> query) {
        Class<T> domainClass = entityInformation.getJavaType();
        if (ClassUtils.isAssignable(Sortable.class, domainClass)) {
            Sort sortNoAsc = Sort.by(Sort.Direction.ASC, "sortNo");
            query = querydsl.applySorting(sortNoAsc, query);
        }
        return query;
    }
}
