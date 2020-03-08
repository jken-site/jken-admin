package jken.module.wechat.web;

import com.querydsl.core.types.Predicate;
import jken.module.wechat.entity.Miniprogram;
import jken.support.web.EntityController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/miniprogram")
public class MiniprogramController extends EntityController<Miniprogram, Long> {
    @Override
    public Page<Miniprogram> list(Predicate predicate, Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }
}
