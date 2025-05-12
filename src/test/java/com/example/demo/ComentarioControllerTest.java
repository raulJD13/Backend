package com.example.demo;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.controllers.ComentarioController;
import com.example.demo.models.Comentario;
import com.example.demo.services.ComentarioService;

@ExtendWith(MockitoExtension.class)
class ComentarioControllerTest {

    @Mock
    private ComentarioService comentarioService;

    @InjectMocks
    private ComentarioController comentarioController;

    private Comentario comentario1;
    private Comentario comentario2;

    @BeforeEach
    void setUp() {
        comentario1 = new Comentario(null, new Date(System.currentTimeMillis()), 1L, "Texto 1", null);
        comentario2 = new Comentario(null, new Date(System.currentTimeMillis()), 2L, "Texto 2", null);
    }

    @Test
    void testGetAllComentarios() {
        when(comentarioService.getAllComentarios()).thenReturn(Arrays.asList(comentario1, comentario2));

        List<Comentario> comentarios = comentarioController.getAllComentarios();

        assertNotNull(comentarios);
        assertEquals(2, comentarios.size());
        verify(comentarioService, times(1)).getAllComentarios();
    }

    @Test
    void testGetComentarioById() {
        when(comentarioService.getComentarioById(1L)).thenReturn(comentario1);

        Comentario comentario = comentarioController.getComentarioById(1L);

        assertNotNull(comentario);
        assertEquals("Texto 1", comentario.getTexto());
        verify(comentarioService, times(1)).getComentarioById(1L);
    }
}
