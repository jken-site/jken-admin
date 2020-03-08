/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-05T19:35:47.651+08:00
 */

package jken.module.core.web;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import jken.module.core.entity.Role;
import jken.module.core.entity.User;
import jken.support.web.EntityController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController extends EntityController<User, Long> {

    @Autowired
    private AuditorAware<User> auditorAware;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/info", produces = "text/html")
    public String showInfo(Model model) {
        User currentUser = auditorAware.getCurrentAuditor().orElseThrow(RuntimeException::new);
        model.addAttribute("entity", currentUser);
        return getViewDir() + "/info";
    }

    @PutMapping(value = "/info")
    @ResponseBody
    public void saveInfo(@ModelAttribute @Valid User entity, BindingResult bindingResult) {
        User currentUser = auditorAware.getCurrentAuditor().orElseThrow(RuntimeException::new);
        currentUser.setName(entity.getName());
        currentUser.setAge(entity.getAge());
        currentUser.setMail(entity.getMail());
        currentUser.setMobile(entity.getMobile());
        currentUser.setIntroduction(entity.getIntroduction());
        getService().save(currentUser);
    }

    @GetMapping(value = "/password", produces = "text/html")
    public String editPassword(Model model) {
        return getViewDir() + "/password";
    }

    @PutMapping(value = "/password")
    @ResponseBody
    public void savePassword(String oldPassword, String newPassword) {
        User currentUser = auditorAware.getCurrentAuditor().orElseThrow(RuntimeException::new);
        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            throw new RuntimeException("旧密码不正确");
        }

        currentUser.setPassword(passwordEncoder.encode(newPassword));
        getService().save(currentUser);
    }

    @Override
    public Page<User> list(Predicate predicate, @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }

    @Override
    protected void onShowEdit(User entity, Model model) {
        super.onShowEdit(entity, model);
        model.addAttribute("entityRoleIds", entity.isNew() ? Lists.newArrayList() : entity.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
    }

    @Override
    protected void onValidate(User entity, BindingResult bindingResult) {
        super.onValidate(entity, bindingResult);
        if (entity.isNew()) {
            String rawPassword = getRequest().getParameter("newPassword");
            if (Strings.isNullOrEmpty(rawPassword)) {
                bindingResult.addError(new FieldError("entity", "password", "密码不能为空"));
            }
        }
    }

    @Override
    protected void onBeforeSave(User entity) {
        super.onBeforeSave(entity);

        String rawPassword = getRequest().getParameter("newPassword");
        if (!Strings.isNullOrEmpty(rawPassword)) {
            String encodedPassword = passwordEncoder.encode(rawPassword);
            entity.setPassword(encodedPassword);
        }
    }

    @PostMapping(params = "!password")
    @ResponseBody
    @Override
    public void create(@ModelAttribute @Valid User entity, BindingResult bindingResult) {
        super.create(entity, bindingResult);
    }

    @PutMapping(value = "/{id}", params = "!password")
    @ResponseBody
    @Override
    public void update(@ModelAttribute("id") @Valid User entity, BindingResult bindingResult) {
        super.update(entity, bindingResult);
    }
}
