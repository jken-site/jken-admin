package jken.module.cms.repo;

import com.querydsl.core.types.dsl.StringExpression;
import jken.module.cms.entity.QStation;
import jken.module.cms.entity.Station;
import jken.support.data.jpa.QuerydslEntityRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface StationRepository extends QuerydslEntityRepository<Station, Long>, QuerydslBinderCustomizer<QStation> {

    @Override
    default void customize(QuerydslBindings bindings, QStation root) {
        bindings.bind(root.name).first(StringExpression::contains);
    }
}
