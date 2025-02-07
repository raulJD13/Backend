package com.example.demo.controllers;

import com.example.demo.models.Usuario;
import com.example.demo.services.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final SecretKey secretKey;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, SecretKey secretKey) {
        this.usuarioService = usuarioService;
        this.secretKey = secretKey;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String contraseña = requestBody.get("contraseña");

        // Verificar si el usuario existe en la base de datos
        Usuario usuario = usuarioService.findByEmail(email);
        if (usuario == null || !usuario.getContraseña().equals(contraseña)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Credenciales incorrectas\"}");
        }

        // Si el usuario existe y la contraseña es correcta, generamos el token
        String token = getJWTToken(email);
        return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
    }

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        String token = getJWTToken(usuario.getEmail());
        usuario.setToken(token);
        return usuarioService.createUsuario(usuario);
    }

    @PutMapping("/{id}")
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
    }

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
