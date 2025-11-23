package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // CAMBIO IMPORTANTE: Usa "/**" en lugar de "/*" para cubrir todas las subrutas
                registry.addMapping("/**")
                        // Define quién tiene permiso de entrar (Tu React)
                        .allowedOrigins("http://localhost:5173", "http://127.0.0.1:5173")
                        // Define qué acciones pueden hacer
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        // Permitir todos los encabezados (Tokens, etc.)
                        .allowedHeaders("*")
                        // Permitir credenciales (necesario para algunos navegadores/auth)
                        .allowCredentials(true);
            }
        };
    }
}