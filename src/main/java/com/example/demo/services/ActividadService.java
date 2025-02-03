
package com.example.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.ActividadRepository;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    public List<Actividad> getActividadesByDeporte(Long idDeporte) {
        return actividadRepository.findByDeporteIdDeporte(idDeporte);
    }
}
    