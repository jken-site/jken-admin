package jken.module.cms.web;

import com.querydsl.core.types.Predicate;
import jken.module.cms.entity.Channel;
import jken.support.web.TreeController;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/channel")
public class ChannelController extends TreeController<Channel, Long> {
    @Override
    public List<Object> list(@QuerydslPredicate(root = Channel.class) Predicate predicate, Sort sort) {
        return super.doList(predicate, sort);
    }

    @Override
    public List<Object> tree(@QuerydslPredicate(root = Channel.class) Predicate predicate, Sort sort) {
        return super.doTree(predicate, sort);
    }

    @Override
    protected void extraListConvert(Map<String, Object> data, Channel entity) {
        data.put("name", entity.getName());
        data.put("path", entity.getPath());
    }

    @Override
    protected String treeNodeDisplay(Channel entity) {
        return entity.getName();
    }
}
