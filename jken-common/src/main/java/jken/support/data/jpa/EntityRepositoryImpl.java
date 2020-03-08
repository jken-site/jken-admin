/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.423+08:00
 */

package jken.support.data.jpa;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import jken.security.CorpCodeHolder;
import jken.support.data.Corpable;
import jken.support.data.Lockedable;
import jken.support.data.LogicDeleteable;
import jken.support.data.Sortable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.ClassUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class EntityRepositoryImpl<T, I extends Serializable> extends SimpleJpaRepository<T, I> implements EntityRepository<T, I> {
    public EntityRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public <S extends T> S save(S entity) {
        if (entity instanceof Corpable && Strings.isNullOrEmpty(((Corpable) entity).getCorpCode())) {
            ((Corpable) entity).setCorpCode(CorpCodeHolder.getCurrentCorpCode());
        }
        return super.save(entity);
    }

    @Override
    public void delete(T entity) {
        if (entity instanceof Lockedable && ((Lockedable) entity).isLocked()) {
            throw new RuntimeException("cannot delete the locked entity.");
        } else if (entity instanceof LogicDeleteable) {
            ((LogicDeleteable) entity).setDeleted(true);
            super.save(entity);
        } else {
            super.delete(entity);
        }
    }

    @Override
    protected <S extends T> TypedQuery<S> getQuery(Specification<S> spec, Class<S> domainClass, Sort sort) {
        // 排序实体
        if (ClassUtils.isAssignable(Sortable.class, domainClass)) {

            Sort sortNoAsc = Sort.by(Sort.Direction.ASC, "sortNo");
            if (sort == null) {
                sort = sortNoAsc;
            } else {
                if (!Iterables.tryFind(sort, s -> s != null && Objects.equal(s.getProperty(), "sortNo")
                ).isPresent()) {
                    sort.and(sortNoAsc);
                }

            }
        }

        return super.getQuery(makeSpecification(spec, domainClass), domainClass, sort);
    }

    @Override
    protected <S extends T> TypedQuery<Long> getCountQuery(Specification<S> spec, Class<S> domainClass) {
        return super.getCountQuery(makeSpecification(spec, domainClass), domainClass);
    }

    protected <S extends T> Specification<S> makeSpecification(Specification<S> spec, Class<S> domainClass) {
        if (ClassUtils.isAssignable(LogicDeleteable.class, domainClass)) {
            spec = Specification.where(spec).and((root, query, cb) -> cb.isFalse(root.get("deleted")));
        }
        if (ClassUtils.isAssignable(Corpable.class, domainClass)) {
            spec = Specification.where(spec).and((root, query, cb) -> cb.equal(root.get("corpCode"), CorpCodeHolder.getCurrentCorpCode()));
        }
        return spec;
    }


    @Override
    public T createNew() {
        try {
            return getDomainClass().getDeclaredConstructor((Class<?>[]) null).newInstance((Object[]) null);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
