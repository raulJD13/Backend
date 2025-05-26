package com.example.demo.controllers;

import com.example.demo.models.Comentario;
import com.example.demo.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    // Obtener todos los comentarios
    @GetMapping
    public List<Comentario> getAllComentarios() {
        return comentarioService.getAllComentarios();
    }

    // Obtener un comentario por ID
    @GetMapping("/{id}")
    public Comentario getComentarioById(@PathVariable Long id) {
        return comentarioService.getComentarioById(id);
    }

    // Crear un nuevo comentario asociado a un usuario
    @PostMapping
    public Comentario createComentario(@RequestBody Comentario comentario, 
                                       @RequestParam Long idUsuario) {
        return comentarioService.createComentario(comentario, idUsuario);
    }

    // Actualizar un comentario existente y opcionalmente cambiar el usuario asociado
    @PutMapping("/{id}")
    public Comentario updateComentario(@PathVariable Long id, 
                                       @RequestBody Comentario comentario, 
                                       @RequestParam(required = false) Long idUsuario) {
        return comentarioService.updateComentario(id, comentario, idUsuario);
    }

    // Eliminar un comentario
    @DeleteMapping("/{id}")
    public void deleteComentario(@PathVariable Long id) {
        comentarioService.deleteComentario(id);
    }
    
    @GetMapping("/actividad/{idActividad}")
    public List<Comentario> getComentariosByActividad(@PathVariable Long idActividad) {
        return comentarioService.getComentariosByActividadId(idActividad);
    }
}