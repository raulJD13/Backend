
package com.example.demo.repository;

import com.example.demo.models.Actividad;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    List<Actividad> findByDeporteIdDeporte(Long idDeporte);
}
