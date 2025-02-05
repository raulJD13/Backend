package com.example.demo.controllers;

import com.example.demo.services.ActividadService;
import com.example.demo.models.Actividad;
import com.example.demo.models.Comentario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/actividades")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    // Obtener actividades por deporte
    @GetMapping("/deporte/{idDeporte}")
    public List<Actividad> getActividadesByDeporte(@PathVariable Long idDeporte) {
        return actividadService.getActividadesByDeporte(idDeporte);
    }

    // Obtener una actividad por ID
    @GetMapping("/{id}")
    public Actividad getActividadById(@PathVariable Long id) {
        return actividadService.getActividadById(id);
    }

    // Crear una nueva actividad
    @PostMapping
    public Actividad createActividad(@RequestBody Actividad actividad) {
        return actividadService.createActividad(actividad);
    }

    // Actualizar una actividad existente
    @PutMapping("/{id}")
    public Actividad updateActividad(@PathVariable Long id, @RequestBody Actividad actividad) {
        return actividadService.updateActividad(id, actividad);
    }

    // Eliminar una actividad
    @DeleteMapping("/{id}")
    public void deleteActividad(@PathVariable Long id) {
        actividadService.deleteActividad(id);
    }

    // Obtener comentarios de una actividad específica
    @GetMapping("/{id}/comentarios")
    public List<Comentario> getComentariosByActividad(@PathVariable Long id) {
        return actividadService.getComentariosByActividad(id);
    }

}
