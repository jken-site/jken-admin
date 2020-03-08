/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.411+08:00
 */

package jken.security;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DelegatingUserDetailsServiceProxy implements UserDetailsService {

    private volatile UserDetailsService target;

    private ApplicationContext applicationContext;

    private Class<?> userDetailsServiceInterface = UserDetailsService.class;

    public DelegatingUserDetailsServiceProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public DelegatingUserDetailsServiceProxy(ApplicationContext applicationContext, Class<?> userDetailsServiceInterface) {
        this(applicationContext);
        this.userDetailsServiceInterface = userDetailsServiceInterface;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (target == null) {
            synchronized (this) {
                if (target == null) {
                    target = (UserDetailsService) this.applicationContext.getBean(userDetailsServiceInterface);
                    if (target == null) {
                        throw new RuntimeException("userDetailsService not found.");
                    }
                }
            }
        }
        return target.loadUserByUsername(username);
    }
}
