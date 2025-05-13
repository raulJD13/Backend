package com.example.demo.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ActividadEquipamiento;
import com.example.demo.services.ActividadEquipamientoService;

@RestController
@RequestMapping("/api/actividad-equipamientos")
public class ActividadEquipamientoController {

    @Autowired
    private ActividadEquipamientoService service;

    @GetMapping
    public List<ActividadEquipamiento> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActividadEquipamiento> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ActividadEquipamiento create(@RequestBody ActividadEquipamiento ae) {
        return service.save(ae);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActividadEquipamiento> update(@PathVariable Long id, @RequestBody ActividadEquipamiento ae) {
        return service.findById(id).map(existing -> {
            existing.setCantidad(ae.getCantidad());
            existing.setActividad(ae.getActividad());
            existing.setEquipamiento(ae.getEquipamiento());
            return ResponseEntity.ok(service.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
