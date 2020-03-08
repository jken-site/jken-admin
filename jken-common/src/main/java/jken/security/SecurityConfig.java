/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-03T20:13:33.756+08:00
 */

package jken.security;

import com.google.common.collect.Iterables;
import jken.JkenProperties;
import jken.integration.Authority;
import jken.integration.IntegrationService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.function.BiFunction;
import java.util.function.Function;

@EnableConfigurationProperties(JkenProperties.class)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JkenProperties properties;

    public SecurityConfig(JkenProperties properties) {
        this.properties = properties;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        WebSecurity.IgnoredRequestConfigurer ignore = web.ignoring().antMatchers(Iterables.toArray(properties.getSecurity().getIgnorePatterns(), String.class));

        String[] ignorePatterns = Iterables.toArray(IntegrationService.getIgnorePatterns(), String.class);
        if (ignorePatterns != null && ignorePatterns.length > 0) {
            ignore.antMatchers(ignorePatterns);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable().and().formLogin().loginPage("/login").successHandler(authenticationSuccessHandler()).permitAll().and().rememberMe().and().logout().permitAll();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();

        for (Authority authority : IntegrationService.getAuthorities()) {
            Authority.PatternType patternType = authority.getPatternType();
            if (patternType == null) {
                patternType = Authority.PatternType.ANT;
            }
            switch (patternType) {
                case REGEX:
                    registerUrlAuthorization(authority, urlRegistry::regexMatchers, urlRegistry::regexMatchers);
                    break;
                case MVC:
                    registerUrlAuthorization(authority, urlRegistry::mvcMatchers, urlRegistry::mvcMatchers);
                    break;
                case ANT:
                default:
                    registerUrlAuthorization(authority, urlRegistry::antMatchers, urlRegistry::antMatchers);
                    break;
            }
        }
        urlRegistry.antMatchers("/corp", "/corp/*").hasAuthority(Authority.AUTHORITY_SUPER_ADMIN).anyRequest().authenticated();
    }

    private void registerUrlAuthorization(Authority authority, Function<String[], ExpressionUrlAuthorizationConfigurer.AuthorizedUrl> function, BiFunction<HttpMethod, String[], ExpressionUrlAuthorizationConfigurer.AuthorizedUrl> function2) {
        if (authority.getHttpMethods() == null || authority.getHttpMethods().length == 0) {
            function.apply(authority.getPatterns()).hasAuthority(authority.getCode());
        } else {
            for (HttpMethod httpMethod : authority.getHttpMethods()) {
                function2.apply(httpMethod, authority.getPatterns()).hasAuthority(authority.getCode());
            }
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new DelegatingUserDetailsServiceProxy(getApplicationContext(), AbstractUserDetailsService.class)).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CorpCodeSavedRequestAwareAuthenticationSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
