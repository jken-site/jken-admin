/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-03T20:13:33.749+08:00
 */

package jken.integration;

import java.util.Objects;
import java.util.Set;

public class AuthorityUtils {

    public static Authority findByCode(Set<Authority> authorities, String code) {
        for (Authority authority : authorities) {
            if (Objects.equals(code, authority.getCode())) {
                return authority;
            }
        }

        return null;
    }

}
