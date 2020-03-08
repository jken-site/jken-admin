/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-05T19:35:47.649+08:00
 */

package jken.module.core.web;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import jken.integration.Authority;
import jken.integration.AuthorityUtils;
import jken.integration.IntegrationService;
import jken.module.core.entity.MenuItem;
import jken.module.core.entity.QUser;
import jken.module.core.entity.Role;
import jken.module.core.entity.User;
import jken.module.core.service.MenuItemService;
import jken.module.core.service.UserService;
import jken.support.data.TreeHelper;
import jken.support.mvc.DataWrap;
import jken.support.web.EntityController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/role")
public class RoleController extends EntityController<Role, Long> {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private UserService userService;

    @Override
    public Page<Role> list(Predicate predicate, @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }

    /**
     * 获取列表数据
     *
     * @param predicate
     * @return
     */
    @GetMapping(produces = "application/json", params = "_list=true")
    @ResponseBody
    public Iterable<Role> list(@QuerydslPredicate(root = Role.class) Predicate predicate, Sort sort) {
        return getService().findAll(predicate, sort);
    }

    @GetMapping(value = "/{id}/user", produces = "text/html")
    public String showUsers(@PathVariable("id") Role entity, Model model) {
        model.addAttribute("entity", entity);
        return getViewDir() + "/user";
    }

    @GetMapping(value = "/{id}/user", produces = "application/json")
    @ResponseBody
    public Page<User> getUsers(@PathVariable("id") Role entity, @QuerydslPredicate(root = User.class) Predicate predicate, @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable, String keywords) {
        BooleanBuilder booleanBuilder = new BooleanBuilder(predicate);
        booleanBuilder.and(QUser.user.roles.contains(entity));
        if (!Strings.isNullOrEmpty(keywords)) {
            QUser qUser = QUser.user;
            booleanBuilder.and(qUser.name.contains(keywords).or(qUser.username.contains(keywords)).or(qUser.mobile.contains(keywords)).or(qUser.mail.contains(keywords)));
        }
        return userService.findAll(booleanBuilder, pageable);
    }

    @GetMapping(value = "/{id}/authority", produces = "text/html")
    public String showAuthorities(@PathVariable("id") Role entity, Model model) {
        model.addAttribute("entity", entity);
        return getViewDir() + "/authority";
    }

    @GetMapping(value = "/{id}/authority", produces = "application/json")
    @ResponseBody
    public DataWrap getAuthorities(@PathVariable("id") Role entity, @QuerydslPredicate(root = MenuItem.class) Predicate predicate) {
        Iterable<MenuItem> roots = menuItemService.findAll(predicate);
        List<MenuItem> menus = TreeHelper.toTree(Lists.newArrayList(roots));
        Set<Authority> authorities = IntegrationService.getAuthorities();
        return DataWrap.of(convertToAuth(menus, entity.getMenuItems(), authorities, entity.getAuthorities()));
    }

    @PostMapping(value = "/{id}/authority")
    @ResponseBody
    public void updateAuthorities(@PathVariable("id") Role entity, @RequestParam(value = "items[]") String[] items) {
        if (items == null || items.length == 0) {
            entity.setMenuItems(null);
            entity.setAuthorities(null);
        } else {
            Set<String> set = Sets.newHashSet(items);

            List<String> authorities = Sets.filter(set, item -> StringUtils.startsWith(item, "authority-")).stream().map(item -> StringUtils.removeStart(item, "authority-")).collect(Collectors.toList());
            if(authorities.contains(Authority.SUPER_ADMIN)) {
                throw new RuntimeException("cannot contains super admin authority.");
            }
            entity.setAuthorities(authorities);

            Set<Long> menuIds = Sets.filter(set, item -> StringUtils.startsWith(item, "menu-")).stream().map(item -> Long.valueOf(StringUtils.removeStart(item, "menu-"))).collect(Collectors.toSet());
            entity.setMenuItems(menuItemService.findAllById(menuIds));
        }
        getService().save(entity);
    }

    private List<Map<String, Object>> convertToAuth(final List<MenuItem> menus, final List<MenuItem> selectedMenuItems, final Set<Authority> authorities, final List<String> selectedAuthorities) {
        if (menus == null || menus.size() == 0) {
            return null;
        }
        return menus.stream().map(item -> {
            Map<String, Object> data = new HashMap<>();
            data.put("value", "menu-" + item.getId());
            data.put("name", item.getName());
            data.put("checked", selectedMenuItems != null && selectedMenuItems.contains(item));
            data.put("disabled", false);

            List<Map<String, Object>> list = convertToAuth(item.getChildren(), selectedMenuItems, authorities, selectedAuthorities);

            String authString = item.getAuthorities();
            if (!Strings.isNullOrEmpty(authString)) {
                String[] auths = authString.split(",");
                if (list == null) {
                    list = new ArrayList<>();
                }
                for (String auth : auths) {
                    Authority authority = AuthorityUtils.findByCode(authorities, auth);
                    if (authority != null) {
                        Map<String, Object> authMap = new HashMap<>();
                        authMap.put("value", "authority-" + authority.getCode());
                        authMap.put("name", authority.getName());
                        authMap.put("checked", selectedAuthorities != null && selectedAuthorities.contains(authority.getCode()));
                        authMap.put("disabled", false);
                        authMap.put("auth", true);
                        list.add(authMap);
                    }
                }
            }
            if (list != null && list.size() > 0) {
                data.put("list", list);
            }
            return data;
        }).collect(Collectors.toList());
    }
}
