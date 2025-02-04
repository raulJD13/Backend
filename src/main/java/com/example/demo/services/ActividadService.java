package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Actividad;
import com.example.demo.repository.ActividadRepository;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    // Obtener actividades por deporte
    public List<Actividad> getActividadesByDeporte(Long idDeporte) {
        return actividadRepository.findByDeporteIdDeporte(idDeporte);
    }

    // Obtener una actividad por ID
    public Actividad getActividadById(Long id) {
        return actividadRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Actividad no encontrada con id: " + id));
    }

    // Crear una nueva actividad
    public Actividad createActividad(Actividad actividad) {
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