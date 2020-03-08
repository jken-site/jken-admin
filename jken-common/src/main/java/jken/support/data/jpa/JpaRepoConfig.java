/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.425+08:00
 */

package jken.support.data.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "jken", repositoryFactoryBeanClass = DataRepositoryFactoryBean.class)
@EnableJpaAuditing
public class JpaRepoConfig {

}
