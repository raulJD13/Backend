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

import com.example.demo.controllers.ActividadController;
import com.example.demo.models.Actividad;
import com.example.demo.services.ActividadService;

@ExtendWith(MockitoExtension.class)
class ActividadControllerTest {

    @Mock
    private ActividadService actividadService;

    @InjectMocks
    private ActividadController actividadController;

    private Actividad actividad1;
    private Actividad actividad2;

    @BeforeEach
    void setUp() {
        actividad1 = new Actividad();
        actividad1.setIdActividad(1L);
        actividad1.setNombre("Fútbol");

        actividad2 = new Actividad();
        actividad2.setIdActividad(2L);
        actividad2.setNombre("Baloncesto");
    }

    @Test
    void testGetAllActividades() {
        when(actividadService.getAllActividades()).thenReturn(Arrays.asList(actividad1, actividad2));

        List<Actividad> actividades = actividadController.getAllActividades();

        assertNotNull(actividades);
        assertEquals(2, actividades.size());
        verify(actividadService, times(1)).getAllActividades();
    }

    @Test
    void testGetActividadById() {
        when(actividadService.getActividadById(1L)).thenReturn(actividad1);

        Actividad actividad = actividadController.getActividadById(1L);

        assertNotNull(actividad);
        assertEquals("Fútbol", actividad.getNombre());
        verify(actividadService, times(1)).getActividadById(1L);
    }
}
