package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Equipamiento;
import com.example.demo.repository.EquipamientoRepository;  

@Service
public class EquipamientoService {

    @Autowired
    private EquipamientoRepository repository;

    public List<Equipamiento> findAll() {
        return repository.findAll();
    }

    public Optional<Equipamiento> findById(Long id) {
        return repository.findById(id);
    }

    public Equipamiento save(Equipamiento equipamiento) {
        return repository.save(equipamiento);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
