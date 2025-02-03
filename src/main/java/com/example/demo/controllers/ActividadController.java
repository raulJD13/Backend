package com.example.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/actividades")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @GetMapping("/deporte/{idDeporte}")
    public List<Actividad> getActividadesByDeporte(@PathVariable Long idDeporte) {
        return actividadService.getActividadesByDeporte(idDeporte);
    }
}
