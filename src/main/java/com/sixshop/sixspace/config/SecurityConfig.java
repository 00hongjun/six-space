package com.sixshop.sixspace.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .mvcMatchers("/", "/info", "/account/**", "/signup").permitAll()
            .mvcMatchers("/admin").hasRole("USER")
            .mvcMatchers("/user").hasRole("USER");

        http
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .headers().frameOptions().disable();

        http
            .exceptionHandling()
            .accessDeniedPage("/denied");
    }
}
