package jken.module.cms.repo;

import com.querydsl.core.types.dsl.StringExpression;
import jken.module.cms.entity.Content;
import jken.module.cms.entity.QContent;
import jken.support.data.jpa.QuerydslEntityRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ContentRepository extends QuerydslEntityRepository<Content, Long>, QuerydslBinderCustomizer<QContent> {

    @Override
    default void customize(QuerydslBindings bindings, QContent root) {
        bindings.bind(root.title).first(StringExpression::contains);
    }
}
