/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.405+08:00
 */

package jken.security;

import com.google.common.base.Strings;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CorpCodeHolder {
    private static final ThreadLocal<String> HOLDER = new NamedThreadLocal<>("corp");

    public static String getCorpCode() {
        return HOLDER.get();
    }

    public static void setCorpCode(String corpCode) {
        HOLDER.set(corpCode);
    }

    public static void cleanup() {
        HOLDER.remove();
    }

    public static String getCurrentCorpCode() {
        String corpCode = getCorpCode();
        if (Strings.isNullOrEmpty(corpCode)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal != null && principal instanceof CustomUserDetails) {
                    corpCode = ((CustomUserDetails<?>) principal).getCorpCode();
                    setCorpCode(corpCode);
                }
            }
            if (Strings.isNullOrEmpty(corpCode)) {


            }

        }


        return corpCode;
    }

    public static String obtainCorpCode() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String corpCode = null;
        if (requestAttributes != null) {
            if (requestAttributes instanceof ServletRequestAttributes) {
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

                corpCode = request.getHeader(CustomUserDetails.CORP_CODE_HEADER);
                if (Strings.isNullOrEmpty(corpCode)) {
                    corpCode = request.getParameter(CustomUserDetails.CORP_CODE_REQUEST_PARAMETER);
                }

                if (Strings.isNullOrEmpty(corpCode)) {
                    Cookie cookie = WebUtils.getCookie(request, CustomUserDetails.CORP_CODE_COOKIE);
                    if (cookie != null) {
                        corpCode = cookie.getValue();
                    }
                }
            }
        }

        return corpCode;
    }
}
