package com.example.CrudOrders.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Configura los orígenes permitidos para las solicitudes CORS
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:3000"));

        // metodos HTTP permitidos para las solicitudes CORS
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));

        // Crea una instancia de UrlBasedCorsConfigurationSource
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Registra la configuración CORS para todas las rutas
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
