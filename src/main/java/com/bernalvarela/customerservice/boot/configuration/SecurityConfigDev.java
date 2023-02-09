package com.bernalvarela.customerservice.boot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev")
public class SecurityConfigDev {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .build();
    }
}
