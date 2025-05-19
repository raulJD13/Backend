package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TerraSplashApplication {
    public static void main(String[] args) {
        SpringApplication.run(TerraSplashApplication.class, args);
    }
<<<<<<< Updated upstream
=======

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
>>>>>>> Stashed changes
}

