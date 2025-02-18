package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity

public class Deporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeporte;

    @Column(nullable = false)
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    public Deporte() {
    }

    
    




	@Column(nullable = false)
	@Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "El tipo solo puede contener letras y espacios")

    private String tipo;

    private String imagen;
    
    public Deporte(Long idDeporte, String nombre, String tipo, String imagen) {
		super();
		this.idDeporte = idDeporte;
		this.nombre = nombre;
		this.tipo = tipo;
		this.imagen = imagen;
		
		
	}

    // Getters y setters
    public Long getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(Long idDeporte) {
        this.idDeporte = idDeporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @OneToMany(mappedBy = "deporte", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Actividad> actividades = new ArrayList<>();

}
