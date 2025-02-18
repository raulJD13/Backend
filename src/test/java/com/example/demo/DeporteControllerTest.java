package com.example.demo;

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

import com.example.demo.controllers.DeporteController;
import com.example.demo.models.Deporte;
import com.example.demo.services.DeporteService;

@ExtendWith(MockitoExtension.class)
class DeporteControllerTest {

    @Mock
    private DeporteService deporteService;

    @InjectMocks
    private DeporteController deporteController;

    private Deporte deporte1;
    private Deporte deporte2;

    @BeforeEach
    void setUp() {
        deporte1 = new Deporte(1L, "url1", "Fútbol", "Equipo");
        deporte2 = new Deporte(2L, "url2", "Basketball", "Equipo");
    }

    @Test
    void testGetAllDeportes() {
        when(deporteService.getAllDeportes()).thenReturn(Arrays.asList(deporte1, deporte2));

        List<Deporte> deportes = deporteController.getAllDeportes();

        assertNotNull(deportes);
        assertEquals(2, deportes.size());
        verify(deporteService, times(1)).getAllDeportes();
    }

  
}

