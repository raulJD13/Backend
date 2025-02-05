
package com.example.demo.controllers;

import com.example.demo.models.Comentario;
import com.example.demo.models.Usuario;
import com.example.demo.services.ComentarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    // Obtener todos los usuarios
    @GetMapping
    public List<Comentario> getAllComentario() {
        return comentarioService.getAllComentarios();
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public Comentario getComentarioById(@PathVariable Long id) {
        return comentarioService.getComentarioById(id);
    }

    // Crear un nuevo usuario
    @PostMapping
    public Comentario createComentario(@RequestBody Comentario comentario) {
        return comentarioService.createComentario(comentario);
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public Comentario updateComentario(@PathVariable Long id, @RequestBody Comentario comentario) {
        return comentarioService.updateComentario(id, comentario);
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public void deleteComentario(@PathVariable Long id) {
        comentarioService.deleteComentario(id);
    }
}

