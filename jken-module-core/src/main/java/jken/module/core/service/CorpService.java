/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.485+08:00
 */

package jken.module.core.service;

import com.google.common.collect.Sets;
import jken.integration.Authority;
import jken.integration.IntegrationService;
import jken.integration.JkenModule;
import jken.module.core.entity.*;
import jken.module.core.repo.CorpRepository;
import jken.support.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorpService extends CrudService<Corp, Long> {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private DictService dictService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CorpRepository corpRepository;

    public Corp findByCode(String code) {
        return corpRepository.findByCode(code);
    }

    @Transactional
    public Corp createNewCorp(String name, String code, String adminUsername, String adminPassword) {
        Corp corp = buildCorp(name, code);

        List<JkenModule> modules = IntegrationService.getModules();
        List<String> authorities = IntegrationService.getAuthorities().stream().map(Authority::getCode).collect(Collectors.toList());

        List<MenuItem> menuItems = buildMenuItems(code, modules);

        Role role = buildAdminRole(code, menuItems, authorities);

        buildAdmin(code, role, adminUsername, adminPassword);

        buildDicts(code, modules);
        return corp;
    }

    protected Corp buildCorp(String name, String code) {
        Corp corp = createNew();
        corp.setName(name);
        corp.setCode(code);
        corp.setStatus(Corp.Status.NORMAL);
        return save(corp);
    }

    protected List<MenuItem> buildMenuItems(String corpCode, List<JkenModule> modules) {
        final List<MenuItem> menuItems = new ArrayList<>();
        Integer sortNo = 1;
        for (JkenModule module : modules) {
            List<JkenModule.Mi> mis = module.getMenus();
            if (mis != null) {
                buildMenus(menuItems, mis, null, corpCode, sortNo);
                sortNo++;
            }
        }
        return menuItems;
    }

    private void buildMenus(final List<MenuItem> result, List<JkenModule.Mi> mis, MenuItem parent, String corpCode, Integer sortNo) {
        for (JkenModule.Mi mi : mis) {
            MenuItem menuItem = buildMenuItem(mi.getName(), mi.getCode(), mi.getHref(), mi.getIconCls(), mi.getAuthorities(), sortNo, corpCode, parent);
            sortNo++;
            result.add(menuItem);
            if (mi.getChildren() != null) {
                int subSort = 1;
                buildMenus(result, mi.getChildren(), menuItem, corpCode, subSort);
                subSort++;
            }
        }
    }

    protected Role buildAdminRole(String corpCode, List<MenuItem> menuItems, List<String> authorities) {
        Role role = roleService.createNew();
        role.setLocked(true);
        role.setName("管理员");
        role.setCode(Authority.AUTHORITY_ADMIN);
        role.setMenuItems(menuItems);
        role.setAuthorities(authorities);
        role.setCorpCode(corpCode);
        roleService.save(role);
        return role;
    }

    protected User buildAdmin(String corpCode, Role adminRole, String username, String password) {
        User user = userService.createNew();
        user.setName("管理员");
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setLocked(true);
        user.setRoles(Sets.newHashSet(adminRole));
        user.setCorpCode(corpCode);
        return userService.save(user);
    }

    private MenuItem buildMenuItem(String name, String code, String href, String iconCls, String authorities, Integer sortNo, String corpCode, MenuItem parent) {
        MenuItem mi = menuItemService.createNew();
        mi.setName(name);
        mi.setCode(code);
        mi.setHref(href);
        mi.setIconCls(iconCls);
        mi.setCorpCode(corpCode);
        mi.setSortNo(sortNo * 100);
        mi.setAuthorities(authorities);
        mi.setParent(parent);
        menuItemService.save(mi);
        return mi;
    }

    private void buildDicts(String corpCode, List<JkenModule> modules) {
        modules.forEach(module -> {
            if (module.getDicts() != null) {
                module.getDicts().forEach(dict -> {
                    Dict dictEntity = dictService.createNew();
                    dictEntity.setName(dict.getName());
                    dictEntity.setCode(dict.getCode());
                    dictEntity.setLocked(true);
                    dictEntity.setCorpCode(corpCode);
                    dictEntity.setItems(dict.getItems().stream().map(item -> {
                        DictItem dictItem = new DictItem();
                        dictItem.setName(item.getName());
                        dictItem.setValue(item.getValue());
                        return dictItem;
                    }).collect(Collectors.toList()));
                    dictService.save(dictEntity);
                });
            }
        });
    }

}
