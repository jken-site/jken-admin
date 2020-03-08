/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-04T15:00:37.477+08:00
 */

package jken.integration;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Objects;

public class Authority {

    public static final String AUTHORITY_SUPER_ADMIN = "SUPER_ADMIN";
    public static final String AUTHORITY_ADMIN = "ADMIN";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final SimpleGrantedAuthority SUPER_ADMIN = new SimpleGrantedAuthority(AUTHORITY_SUPER_ADMIN);

    private String name;
    private String code;
    private String[] patterns;
    private PatternType patternType = PatternType.ANT;
    private HttpMethod[] httpMethods = new HttpMethod[]{HttpMethod.GET};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getPatterns() {
        return patterns;
    }

    public void setPatterns(String[] patterns) {
        this.patterns = patterns;
    }

    public PatternType getPatternType() {
        return patternType;
    }

    public void setPatternType(PatternType patternType) {
        this.patternType = patternType;
    }

    public HttpMethod[] getHttpMethods() {
        return httpMethods;
    }

    public void setHttpMethods(HttpMethod[] httpMethods) {
        this.httpMethods = httpMethods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority = (Authority) o;
        return code.equals(authority.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public enum PatternType {
        ANT, REGEX, MVC
    }
}
