package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.ActividadEquipamiento;
import com.example.demo.repository.ActividadEquipamientoRepository;

@Service
public class ActividadEquipamientoService {

    @Autowired
    private ActividadEquipamientoRepository repository;

    public List<ActividadEquipamiento> findByActividadId(Long actividadId) {
        return repository.findAllByActividad_IdActividad(actividadId);
    }

    public List<ActividadEquipamiento> findAll() {
        return repository.findAll();
    }

    public Optional<ActividadEquipamiento> findById(Long id) {
        return repository.findById(id);
    }

    public ActividadEquipamiento save(ActividadEquipamiento ae) {
        return repository.save(ae);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
