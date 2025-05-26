package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.JWTAuthorizationFilter;
import java.util.Arrays;

import javax.crypto.SecretKey;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication
@RestController
public class TerraSplashApplication {

    public static void main(String[] args) {
        SpringApplication.run(TerraSplashApplication.class, args);
    }

    // Configuración de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            JWTAuthorizationFilter jwtFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
            var cfg = new CorsConfiguration();
            cfg.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8100"));
            cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            cfg.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
            cfg.setAllowCredentials(true);
            return cfg;
        }))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                // login y registro
                .requestMatchers(HttpMethod.POST, "/api/usuarios/login", "/api/usuarios").permitAll()
                // servir imágenes desde /uploads/**
                .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()
                // el resto, con JWT
                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Bean de JWTAuthorizationFilter para inyectar la clave secreta
    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter(SecretKey secretKey) {
        return new JWTAuthorizationFilter(secretKey);
    }
}
