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

import javax.crypto.SecretKey;

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
            .csrf(csrf -> csrf.disable()) // Deshabilita CSRF para APIs REST
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No usa sesiones
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/usuarios/login").permitAll() // Permite login sin autenticación
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll() // Permite crear usuario sin autenticación
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
