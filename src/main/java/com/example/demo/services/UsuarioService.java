package com.example.demo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.models.Usuario;
import com.example.demo.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener todos los usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener un usuario por ID
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Usuario no encontrado con id: " + id));
    }

    // Buscar un usuario por email (para autenticación)
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Crear un nuevo usuario
    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Actualizar un usuario existente
    public Usuario updateUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Usuario no encontrado con id: " + id));

        // Actualizar campos
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setLocalizacion(usuario.getLocalizacion());
        usuarioExistente.setContraseña(usuario.getContraseña());
        usuarioExistente.setFotoPerfil(usuario.getFotoPerfil());
        usuarioExistente.setFotoFondoPerfil(usuario.getFotoFondoPerfil());
        usuarioExistente.setToken(usuario.getToken());
        usuarioExistente.setActividades(usuario.getActividades());  // Establecer las actividades asociadas

        return usuarioRepository.save(usuarioExistente);
    }

    // Eliminar un usuario
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}