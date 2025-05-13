package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Equipamiento;

@Repository
public interface EquipamientoRepository extends JpaRepository<Equipamiento, Long> {
}