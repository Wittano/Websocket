package com.websocket.websocket.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService service;

    public SecurityConfig(@Qualifier("userDetailService") UserDetailsService service) {
        this.service = service;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                    .loginPage("/")
                    .loginProcessingUrl("/verify")
                    .defaultSuccessUrl("/main/home", true)
                    .failureForwardUrl("/fail")
                    .permitAll()
                .and()
                    .authorizeRequests()
                    .antMatchers("/main/**", "/message/**").authenticated()
                    .antMatchers("/verify", "/").permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .exceptionHandling().accessDeniedPage("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder());
    }
}
