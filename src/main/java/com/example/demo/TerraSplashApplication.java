package com.example.demo;

import java.util.Arrays;

import javax.crypto.SecretKey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;

import com.example.demo.security.JWTAuthorizationFilter;

@SpringBootApplication
@RestController
public class TerraSplashApplication {

    public static void main(String[] args) {
        SpringApplication.run(TerraSplashApplication.class, args);
    }

    // Configuración de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JWTAuthorizationFilter jwtAuthorizationFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
            var corsConfig = new CorsConfiguration();
            corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:8100"));
            corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
            corsConfig.setAllowCredentials(true);
            return corsConfig;
        }))
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF para APIs REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No usa sesiones
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/usuarios/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()
                .anyRequest().authenticated() // Exige autenticación para cualquier otra solicitud
                )
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class); // Usa el filtro con clave inyectada

        return http.build();
    }

    // Bean de JWTAuthorizationFilter para inyectar la clave secreta
    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter(SecretKey secretKey) {
        return new JWTAuthorizationFilter(secretKey);
    }
}
