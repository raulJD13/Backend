package com.example.demo.models;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;



@Entity

@Table(name = "ActividadEquipamiento",

       uniqueConstraints = @UniqueConstraint(columnNames = {"actividad_id", "equipamiento_id"}))

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class ActividadEquipamiento {



    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;



    @ManyToOne(fetch = FetchType.EAGER, optional = false)

    @JoinColumn(name = "actividad_id")

    private Actividad actividad;



    @ManyToOne(fetch = FetchType.EAGER, optional = false)

    @JoinColumn(name = "equipamiento_id")

    private Equipamiento equipamiento;



    @Column(nullable = false)

    private Integer cantidad = 1;



    public ActividadEquipamiento() {}



    public ActividadEquipamiento(Actividad actividad, Equipamiento equipamiento, Integer cantidad) {

        this.actividad = actividad;

        this.equipamiento = equipamiento;

        this.cantidad = cantidad != null ? cantidad : 1;

    }



    // Getters y setters



    public Long getId() {

        return id;

    }



    public Actividad getActividad() {

        return actividad;

    }



    public void setActividad(Actividad actividad) {

        this.actividad = actividad;

    }



    public Equipamiento getEquipamiento() {

        return equipamiento;

    }



    public void setEquipamiento(Equipamiento equipamiento) {

        this.equipamiento = equipamiento;

    }



    public Integer getCantidad() {

        return cantidad;

    }



    public void setCantidad(Integer cantidad) {

        this.cantidad = cantidad != null ? cantidad : 1;

    }

}

