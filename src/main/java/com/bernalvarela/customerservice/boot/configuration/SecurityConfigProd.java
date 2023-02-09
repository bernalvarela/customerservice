package com.bernalvarela.customerservice.boot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("prod")
public class SecurityConfigProd {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/customers*")
                .hasRole("user")
                .requestMatchers("/api/v1/users*")
                .hasRole("admin")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}
