/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.408+08:00
 */

package jken.security;

import org.springframework.security.core.AuthenticationException;

public class CorpNotFoundException extends AuthenticationException {

    private static final long serialVersionUID = -5571907595893230781L;

    public CorpNotFoundException(String msg) {
        super(msg);
    }

    public CorpNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

}
