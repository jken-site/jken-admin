/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-05T19:35:47.655+08:00
 */

package jken.module.core.service;

import jken.module.core.entity.User;
import jken.module.core.repo.RoleRepository;
import jken.module.core.repo.UserRepository;
import jken.security.AbstractUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CorpUserDetailsService extends AbstractUserDetailsService<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected User loadRepoUserDetails(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    protected User loadUserByUsernameAndCorpCode(String username, String corpCode) {
        return userRepository.findByUsernameAndCorpCode(username, corpCode);
    }
}
