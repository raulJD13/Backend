package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Equipamiento;
import com.example.demo.services.EquipamientoService;

@RestController
@RequestMapping("/api/equipamientos")
@CrossOrigin(origins = "*")
public class EquipamientoController {

    @Autowired
    private EquipamientoService service;

    @GetMapping
    public List<Equipamiento> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipamiento> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Equipamiento create(@RequestBody Equipamiento equipamiento) {
        return service.save(equipamiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipamiento> update(@PathVariable Long id, @RequestBody Equipamiento eq) {
        return service.findById(id).map(existing -> {
            existing.setNombre(eq.getNombre());
            existing.setTipo(eq.getTipo());
            existing.setDescripcion(eq.getDescripcion());
            existing.setImagen(eq.getImagen());
            return ResponseEntity.ok(service.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
