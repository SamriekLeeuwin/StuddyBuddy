package com.studdybudy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // CSRF uitgeschakeld voor stateless REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/signup",     // open endpoint voor registratie
                                "/api/auth/login",      // open endpoint voor login
                                "/v3/api-docs/**",      // open voor Swagger/OpenAPI docs (optioneel)
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/error"                // laat Spring error pagina toe
                        ).permitAll()
                        .anyRequest().authenticated() // alle andere endpoints vereisen JWT
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt()) // JWT-verificatie activeren
                .build();
    }
}
