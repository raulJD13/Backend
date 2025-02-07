package com.example.demo.controllers;

import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Deporte;
import com.example.demo.services.DeporteService;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/deportes")
@CrossOrigin(origins = "http://localhost:8100\"", allowCredentials = "true")
public class DeporteController {

    @Autowired
    private DeporteService deporteService;

    @GetMapping
    public List<Deporte> getAllDeportes() {
        return deporteService.getAllDeportes();
    }

    @GetMapping("/{id}")
    public Deporte getDeporteById(@PathVariable Long id) {
        return deporteService.getDeporteById(id);
    }

    @PostMapping
    public Deporte createDeporte(@RequestBody Deporte deporte) {
        return deporteService.createDeporte(deporte);
    }

    
    @PutMapping("/{id}")
    public Deporte updateDeporte(@PathVariable Long id, @RequestBody Deporte deporte) {
        Deporte existingDeporte = deporteService.getDeporteById(id);
        if (existingDeporte != null) {
            existingDeporte.setNombre(deporte.getNombre());
            existingDeporte.setTipo(deporte.getTipo());
            existingDeporte.setImagen(deporte.getImagen()); // Guardar URL completa
            return deporteService.updateDeporte(id, existingDeporte);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteDeporte(@PathVariable Long id) {
        deporteService.deleteDeporte(id);
    }
}
