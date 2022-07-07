package br.com.zup.edu.revendacarros;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.cors()
            .and()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .rememberMe().disable()
                .httpBasic().disable()
                .requestCache().disable()
                .headers().frameOptions().deny()
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers(GET, "/api/carros").hasAuthority("SCOPE_carros:read")
                    .antMatchers(POST, "/api/carros").hasAuthority("SCOPE_carros:write")
                .anyRequest()
                    .authenticated()
                .and()
                    .oauth2ResourceServer()
                        .jwt()
        ;
        // @formatter:on
    }
}
