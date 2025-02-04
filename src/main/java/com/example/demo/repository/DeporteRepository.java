
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Deporte;

@Repository
public interface DeporteRepository extends JpaRepository<Deporte, Long> {
}
