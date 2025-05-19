package com.example.demo.controllers;

<<<<<<< Updated upstream
import com.example.demo.services.UsuarioService;
import com.example.demo.models.Usuario;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
=======
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Usuario;
import com.example.demo.services.UsuarioService;

import io.jsonwebtoken.Jwts;
>>>>>>> Stashed changes

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
<<<<<<< Updated upstream
    private UsuarioService usuarioService;
=======
    public UsuarioController(UsuarioService usuarioService, SecretKey secretKey) {
        this.usuarioService = usuarioService;
        this.secretKey = secretKey;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String contraseña = requestBody.get("contraseña");

        Usuario usuario = usuarioService.findByEmail(email);

        if (usuario == null || !usuario.getContraseña().equals(contraseña)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));
        }

        String token = getJWTToken(email);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("idUsuario", usuario.getIdUsuario()); // 👈 Agregamos el ID del usuario

        return ResponseEntity.ok(response);
    }
>>>>>>> Stashed changes

    // Obtener todos los usuarios
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id);
    }

    // Crear un nuevo usuario
    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.createUsuario(usuario);
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(id, usuario);
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
    }
<<<<<<< Updated upstream
}

=======

    private String getJWTToken(String email) {
        List<String> grantedAuthorities = List.of("ROLE_USER");

        return "Bearer " + Jwts.builder()
                .setId("demoJWT")
                .setSubject(email)
                .claim("authorities", grantedAuthorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(secretKey)
                .compact();
    }
}
>>>>>>> Stashed changes
