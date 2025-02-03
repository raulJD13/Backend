
package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.demo.Deporte;
import com.example.demo.DeporteRepository;

@Service
public class DeporteService {

    @Autowired
    private DeporteRepository deporteRepository;

    public List<Deporte> getAllDeportes() {
        return deporteRepository.findAll();
    }

    public Deporte getDeporteById(Long id) {
        return deporteRepository.findById(id).orElse(null);
    }

    public Deporte createDeporte(Deporte deporte) {
        return deporteRepository.save(deporte);
    }

    public Deporte updateDeporte(Long id, Deporte deporte) {
        Deporte existingDeporte = deporteRepository.findById(id).orElse(null);
        if (existingDeporte != null) {
            existingDeporte.setNombre(deporte.getNombre());
            existingDeporte.setTipo(deporte.getTipo());
            existingDeporte.setImagen(deporte.getImagen());
            return deporteRepository.save(existingDeporte);
        }
        return null;
    }

    public void deleteDeporte(Long id) {
        deporteRepository.deleteById(id);
    }
}
