package com.example.demo.controllers;

import com.example.demo.models.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.services.UsuarioService;
import com.example.demo.utils.AuthResponse;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getEmail());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuarioService.verificarContrasena(request.getContraseña(), usuario.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(usuario.getEmail());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
