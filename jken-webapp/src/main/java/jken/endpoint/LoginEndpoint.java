/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.517+08:00
 */

package jken.endpoint;

import com.google.common.base.Strings;
import jken.module.core.entity.Corp;
import jken.module.core.service.CorpService;
import jken.security.CorpCodeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@Controller
public class LoginEndpoint {

    @Autowired
    private CorpService corpService;

    @GetMapping(value = "/login", params = "!error")
    public String loginPage(Model model) {
        String corpCode = CorpCodeHolder.obtainCorpCode();
        Corp corp = Strings.isNullOrEmpty(corpCode) ? null : corpService.findByCode(corpCode);
        if (corp == null) {
            model.addAttribute("corps", corpService.findAll());
        } else {
            model.addAttribute("corp", corp);
        }
        return "login";
    }

    @GetMapping(value = "/login", params = "error")
    public String loginErrorPage(Model model, HttpSession session) {
        String errorMsg = "Invalid credentials";
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            errorMsg = ex != null ? ex.getMessage() : "Invalid credentials";
        }
        model.addAttribute("errorMsg", HtmlUtils.htmlEscape(errorMsg));
        return loginPage(model);
    }
}
