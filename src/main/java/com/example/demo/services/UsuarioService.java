package com.example.demo.services;

import com.example.demo.repository.UsuarioRepository;
import com.example.demo.models.Usuario;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }
    // Obtener todos los usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener un usuario por ID
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    // Crear un nuevo usuario
    public Usuario createUsuario(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    // Actualizar un usuario existente
    public Usuario updateUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Usuario no encontrado con id: " + id));

        // Actualizar campos
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setLocalizacion(usuario.getLocalizacion());
        usuarioExistente.setContrasena(usuario.getContrasena());
        usuarioExistente.setFotoPerfil(usuario.getFotoPerfil());
        usuarioExistente.setFotoFondoPerfil(usuario.getFotoFondoPerfil());

        return usuarioRepository.save(usuarioExistente);
    }

    // Eliminar un usuario
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
    
     public boolean verificarContrasena(String contrasena, String contrasenaEncriptada) {
        return passwordEncoder.matches(contrasena, contrasenaEncriptada);
    }
}
