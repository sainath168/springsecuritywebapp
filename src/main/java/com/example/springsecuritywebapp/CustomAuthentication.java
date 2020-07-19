package com.example.springsecuritywebapp;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class CustomAuthentication extends WebSecurityConfigurerAdapter {

    /**
     * This class extends WebSecurityConfigurerAdapter and overrides the configure methods
     * to get the hold of 2 objects mentioned below,
     *
     * - AuthenticationManagerBuilder (used mainly for authentication purposes)
     * - HttpSecurity (used mainly for authorization purposes)
     */


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // By this configure method we get the hold of AuthenticationManagerBuilder
        // and we can use this for configuring our authentication

        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("user")
                .roles("USER");

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // By this configure method we get the hold of HttpSecurity
        // and we can use this for configuring our authorization

        // "/**" regex tells that match all the current level and any nested level paths
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .and().formLogin();
    }

}
