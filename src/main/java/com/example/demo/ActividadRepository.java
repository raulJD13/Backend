
package com.example.demo;

import java.util.List;
import com.example.demo.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    List<Actividad> findByDeporteIdDeporte(Long idDeporte);
}
