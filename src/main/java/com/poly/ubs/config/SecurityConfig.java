package com.poly.ubs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(config -> config.disable()).cors(config -> config.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/poly/url2").hasRole("ADMIN")
                .requestMatchers("/poly/url3").hasRole("ADMIN")
                .requestMatchers("/poly/url4").hasAnyRole("ADMIN")
                .anyRequest().permitAll()
        );

        http.formLogin(form -> form
                .loginPage("/login/form")
                .loginProcessingUrl("/login/check")
                .defaultSuccessUrl("/login/success", false)
                .failureUrl("/login/failure")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
        );

        http.rememberMe(rm -> rm
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("remember-me")
                .tokenValiditySeconds(3 * 24 * 60 * 60)
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login/exit")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("remember-me")
        );

        http.exceptionHandling(ex ->
                ex.accessDeniedPage("/access-denied.html")
        );

        return http.build();
    }
}
