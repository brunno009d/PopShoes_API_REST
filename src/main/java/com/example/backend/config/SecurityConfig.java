package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider; 

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        // 1. Desactivar CSRF (Correcto para APIs)
        http.csrf(csrf -> csrf.disable());
    
        // 2. IMPORTANTE: Integrar la configuración de CORS de WebConfig aquí
        http.cors(org.springframework.security.config.Customizer.withDefaults()); 
    
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/auth/**",           // Login
                    "/api/usuarios",      // <--- AGREGA ESTO: Permite el registro público
                    "/api/calzados/**",   // (Opcional) Si quieres que ver productos sea público
                    "/swagger-ui/**",     
                    "/v3/api-docs/**",
                    "/swagger-resources/**"
                ).permitAll()
                .anyRequest().authenticated() // El resto sigue privado
        );
    
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    
        return http.build();
    }
}