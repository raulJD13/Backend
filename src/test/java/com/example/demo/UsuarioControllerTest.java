package com.example.demo;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.demo.controllers.UsuarioController;
import com.example.demo.models.Usuario;
import com.example.demo.services.UsuarioService;

import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario1;
    private Usuario usuario2;

    private SecretKey secretKey;

    @BeforeEach
    void setUp() {
        // Actualizamos las instancias incluyendo el valor para "username"
        usuario1 = new Usuario("user1@example.com", "Madrid", "password123", "foto1.jpg", "fondo1.jpg", "token1", "user1");
        usuario2 = new Usuario("user2@example.com", "Barcelona", "password456", "foto2.jpg", "fondo2.jpg", "token2", "user2");

        // Generamos una clave secreta para JWT (simulada para los tests)
        secretKey = Keys.hmacShaKeyFor("Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJkZW1vSldUIiwic3ViIjoicmF1bEB0ZXN0LmNvbSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE3Mzk4OTMyMjIsImV4cCI6MTczOTg5MzgyMn0.pm8Z_EQVV__s8Og41OyPwlKS3CiAM--_nEyTEPGtub9_0_CHk9XnX9MrIELWmLflYMp4OTTwVBpwfVbEc4XqDw".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void testGetAllUsuarios() {
        when(usuarioService.getAllUsuarios()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<Usuario> usuarios = usuarioController.getAllUsuarios();

        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
        verify(usuarioService, times(1)).getAllUsuarios();
    }

    @Test
    void testGetUsuarioById() {
        when(usuarioService.getUsuarioById(1L)).thenReturn(usuario1);

        Usuario usuario = usuarioController.getUsuarioById(1L);

        assertNotNull(usuario);
        assertEquals("user1@example.com", usuario.getEmail());
        verify(usuarioService, times(1)).getUsuarioById(1L);
    }

    @Test
    void testUpdateUsuario() {
        when(usuarioService.updateUsuario(eq(1L), any(Usuario.class))).thenReturn(usuario1);

        // Se añade el parámetro "username" al crear el objeto a actualizar
        Usuario usuarioActualizado = new Usuario("updated@example.com", "Valencia", "passwordUpdated", "fotoUpdated.jpg", "fondoUpdated.jpg", "tokenUpdated", "updated");
        Usuario resultado = usuarioController.updateUsuario(1L, usuarioActualizado);

        assertNotNull(resultado);
        assertEquals("user1@example.com", resultado.getEmail()); 
        verify(usuarioService, times(1)).updateUsuario(eq(1L), any(Usuario.class));
    }

    @Test
    void testDeleteUsuario() {
        doNothing().when(usuarioService).deleteUsuario(1L);

        usuarioController.deleteUsuario(1L);

        verify(usuarioService, times(1)).deleteUsuario(1L);
    }

    @Test
    void testLoginFailure() {
        when(usuarioService.findByEmail("user1@example.com")).thenReturn(usuario1);

        Map<String, String> loginRequest = Map.of("email", "user1@example.com", "contraseña", "wrongpassword");

        ResponseEntity<?> response = usuarioController.login(loginRequest);

        assertEquals(401, response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("error"));
        verify(usuarioService, times(1)).findByEmail("user1@example.com");
    }
}
