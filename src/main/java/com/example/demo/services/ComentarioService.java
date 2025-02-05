package com.example.demo.services;

import com.example.demo.repository.ComentarioRepository;
import com.example.demo.models.Comentario;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    // Obtener todos los usuarios
    public List<Comentario> getAllComentarios() {
        return comentarioRepository.findAll();
    }

    // Obtener un usuario por ID
    public Comentario getComentarioById(Long id) {
        return comentarioRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Comentario no encontrado con id: " + id));
    }

    // Crear un nuevo usuario
    public Comentario createComentario(Comentario comentario) {
        // Aquí puedes realizar la lógica de seguridad (cifrado de contraseña, etc.)
        return comentarioRepository.save(comentario);
    }

    // Actualizar  existente
    public Comentario updateComentario(Long id, Comentario comentario) {
        Comentario comentarioExistente = comentarioRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Comentario no encontrado con id: " + id));
       
        // Actualizar campos
        comentarioExistente.setTexto(comentario.getTexto());
        comentarioExistente.setFecha(comentario.getFecha());
        comentarioExistente.setActividad(comentario.getActividad());
      //  comentarioExistente.setUsuarios(comentario.getUsuarios());
        

        return comentarioRepository.save(comentarioExistente);
    }

    // Eliminar un usuario
    public void deleteComentario(Long id) {
        comentarioRepository.deleteById(id);
    }
}
