package com.example.demo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.models.Actividad;
import com.example.demo.models.Deporte;
import com.example.demo.repository.ActividadRepository;
import com.example.demo.repository.DeporteRepository;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    // Obtener todas las actividades
    public List<Actividad> getAllActividades() {
        return actividadRepository.findAll();
    }

    // Obtener actividades por deporte
    public List<Actividad> getActividadesByDeporte(Long idDeporte) {
        return actividadRepository.findByDeporteIdDeporte(idDeporte);
    }

    // Obtener una actividad por ID
    public Actividad getActividadById(Long id) {
        return actividadRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Actividad no encontrada con id: " + id));
    }

    @Autowired
    private DeporteRepository deporteRepository; // Repositorio para buscar deportes

    public Actividad createActividad(Actividad actividad) {
        if (actividad.getDeporte() == null || actividad.getDeporte().getIdDeporte() == null) {
            throw new RuntimeException("El campo deporte no puede ser nulo.");
        }

        // Buscar el deporte en la base de datos
        Deporte deporte = deporteRepository.findById(actividad.getDeporte().getIdDeporte())
            .orElseThrow(() -> new RuntimeException("Deporte no encontrado con id: " + actividad.getDeporte().getIdDeporte()));

        // Asignar el deporte a la actividad antes de guardarla
        actividad.setDeporte(deporte);

        // Guardar la actividad
        return actividadRepository.save(actividad);
    }
    // Actualizar una actividad existente
    public Actividad updateActividad(Long id, Actividad actividad) {
        Actividad actividadExistente = actividadRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Actividad no encontrada con id: " + id));
       
        // Actualizar campos
        actividadExistente.setNombre(actividad.getNombre());
        actividadExistente.setValoracion(actividad.getValoracion());
        actividadExistente.setPrecio(actividad.getPrecio());
        actividadExistente.setDescripcion(actividad.getDescripcion());
        actividadExistente.setTendencia(actividad.getTendencia());
        actividadExistente.setEvento(actividad.getEvento());
        actividadExistente.setBookmark(actividad.getBookmark());
        actividadExistente.setFavoritas(actividad.getFavoritas());
        actividadExistente.setUnido(actividad.getUnido());
        actividadExistente.setLatitud(actividad.getLatitud());
        actividadExistente.setLongitud(actividad.getLongitud());
        actividadExistente.setDificultad(actividad.getDificultad());
        actividadExistente.setImagen(actividad.getImagen());
        actividadExistente.setDisponibilidad(actividad.getDisponibilidad());
        actividadExistente.setDeporte(actividad.getDeporte());

        return actividadRepository.save(actividadExistente);
    }

    // Eliminar una actividad
    public void deleteActividad(Long id) {
        actividadRepository.deleteById(id);
    }
}
