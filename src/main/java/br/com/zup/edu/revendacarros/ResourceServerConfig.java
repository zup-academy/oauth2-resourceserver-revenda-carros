package br.com.zup.edu.revendacarros;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/carros").hasAuthority("SCOPE_carros:read")
                .antMatchers(HttpMethod.POST, "/api/carros").hasAuthority("SCOPE_carros:write")
            .anyRequest()
                .authenticated()
            .and()
                .oauth2ResourceServer()
                    .jwt()
                    ;
    }
}
