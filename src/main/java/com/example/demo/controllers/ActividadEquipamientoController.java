package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

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

import com.example.demo.models.Actividad;
import com.example.demo.models.ActividadEquipamiento;
import com.example.demo.models.Equipamiento;
import com.example.demo.repository.ActividadRepository;
import com.example.demo.repository.EquipamientoRepository;
import com.example.demo.services.ActividadEquipamientoService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/actividad-equipamientos")
@CrossOrigin(origins = "*")
public class ActividadEquipamientoController {

    private final ActividadEquipamientoService aeService;
    private final ActividadRepository actividadRepo;
    private final EquipamientoRepository equipRepo;

    public ActividadEquipamientoController(
            ActividadEquipamientoService aeService,
            ActividadRepository actividadRepo,
            EquipamientoRepository equipRepo) {
        this.aeService = aeService;
        this.actividadRepo = actividadRepo;
        this.equipRepo = equipRepo;
    }

    @GetMapping("/por-actividad/{actividadId}")
    public List<ActividadEquipamiento> findByActividad(@PathVariable Long actividadId) {
        return aeService.findByActividadId(actividadId);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ActividadEquipamiento> createLink(@RequestBody Map<String,Object> body) {
        try {
            Long actividadId    = Long.parseLong(extract(body, "actividad", "id"));
            Long equipamientoId = Long.parseLong(extract(body, "equipamiento", "id"));
            Integer cantidad    = (Integer) body.getOrDefault("cantidad", 1);

            Actividad actividad = actividadRepo.findById(actividadId)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada"));
            Equipamiento equip = equipRepo.findById(equipamientoId)
                .orElseThrow(() -> new IllegalArgumentException("Equipamiento no encontrado"));

            ActividadEquipamiento ae = new ActividadEquipamiento(actividad, equip, cantidad);
            return ResponseEntity.ok(aeService.save(ae));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /** PUT sin cambiar actividad ni equipamiento ID, solo cantidad */
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ActividadEquipamiento> updateCantidad(
            @PathVariable Long id,
            @RequestBody Map<String,Object> body) {
        return aeService.findById(id).map(ae -> {
            Integer nuevaCant = (Integer) body.get("cantidad");
            if (nuevaCant != null) ae.setCantidad(nuevaCant);
            return ResponseEntity.ok(aeService.save(ae));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        aeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @SuppressWarnings("unchecked")
    private String extract(Map<String,Object> map, String parent, String child) {
        Object p = map.get(parent);
        if (!(p instanceof Map)) throw new IllegalArgumentException(parent + " debe ser un objeto");
        Object c = ((Map<String,Object>)p).get(child);
        if (c == null) throw new IllegalArgumentException(parent + "." + child + " obligatorio");
        return c.toString();
    }
}

