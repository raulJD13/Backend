
package com.example.demo;

import com.example.demo.Deporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeporteRepository extends JpaRepository<Deporte, Long> {
}
