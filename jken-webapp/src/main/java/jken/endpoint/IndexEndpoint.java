/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-04T15:00:37.510+08:00
 */

package jken.endpoint;

import jken.module.core.entity.User;
import jken.module.core.service.MenuItemService;
import jken.support.data.TreeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexEndpoint {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private AuditorAware<User> auditorAware;

    @GetMapping({"", "/"})
    public String indexPage(Model model) {
        User currentUser = auditorAware.getCurrentAuditor().orElseThrow(RuntimeException::new);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("mis", TreeHelper.toTree(currentUser.getMenuItems()));
        return "index";
    }

    @GetMapping({"/home"})
    public String homePage() {
        return "home";
    }
}
