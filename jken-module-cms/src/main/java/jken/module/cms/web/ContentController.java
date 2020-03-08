package jken.module.cms.web;

import com.querydsl.core.types.Predicate;
import jken.module.cms.entity.Content;
import jken.support.web.EntityController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content")
public class ContentController extends EntityController<Content, Long> {
    @Override
    public Page<Content> list(Predicate predicate, Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }
}
