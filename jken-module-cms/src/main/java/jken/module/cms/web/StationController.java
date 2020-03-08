package jken.module.cms.web;

import com.querydsl.core.types.Predicate;
import jken.module.cms.entity.Station;
import jken.support.web.EntityController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/station")
public class StationController extends EntityController<Station, Long> {

    @Override
    public Page<Station> list(Predicate predicate, Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }
}
