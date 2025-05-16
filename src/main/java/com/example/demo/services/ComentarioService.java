package com.example.demo.services;

import com.example.demo.models.Comentario;
import com.example.demo.models.Usuario;
import com.example.demo.repository.ComentarioRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener todos los comentarios
    public List<Comentario> getAllComentarios() {
        return comentarioRepository.findAll();
    }

    // Obtener un comentario por ID
    public Comentario getComentarioById(Long id) {
        return comentarioRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Comentario no encontrado con id: " + id));
    }

    // Crear un nuevo comentario
    public Comentario createComentario(Comentario comentario, Long idUsuario) {
        // Verificar que el usuario exista antes de asignarlo
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));

        comentario.setUsuario(usuario);
        return comentarioRepository.save(comentario);
    }

    // Actualizar un comentario existente
    public Comentario updateComentario(Long id, Comentario comentario, Long idUsuario) {
        Comentario comentarioExistente = comentarioRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Comentario no encontrado con id: " + id));

        // Verificar si se quiere cambiar el usuario asociado al comentario
        if (idUsuario != null) {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));
            comentarioExistente.setUsuario(usuario);
        }

        // Actualizar campos
        comentarioExistente.setTexto(comentario.getTexto());
        comentarioExistente.setFecha(comentario.getFecha());
        comentarioExistente.setActividad(comentario.getActividad());
        comentarioExistente.setUsuario(comentario.getUsuario());

        return comentarioRepository.save(comentarioExistente);
    }

    // Eliminar un comentario
    public void deleteComentario(Long id) {
        comentarioRepository.deleteById(id);
    }
    
 // Obtener comentarios por id de actividad
    public List<Comentario> getComentariosByActividadId(Long idActividad) {
        return comentarioRepository.findByActividad_IdActividad(idActividad);
    }
}
