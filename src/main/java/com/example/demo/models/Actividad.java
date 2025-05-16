package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idActividad")
@Entity
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActividad;

    @Column(nullable = false)
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    private Float valoracion;

    @PositiveOrZero(message = "El precio no puede ser negativo")
    private Double precio;

    private String descripcion;

    private Boolean tendencia = false;
    private Boolean evento = false;
    private Boolean bookmark = false;
    private Boolean favoritas = false;
    private Boolean unido = false;

    private Double latitud;
    private Double longitud;

    @Enumerated(EnumType.STRING)
    private Dificultad dificultad;

    private String imagen;
    private Boolean disponibilidad = true;

    @ManyToOne
    @JoinColumn(name = "id_deporte", nullable = false)
    private Deporte deporte;

    @ManyToMany
    @JoinTable(
        name = "usuarioactividad",
        joinColumns = @JoinColumn(name = "actividad_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> usuarios;

    public Actividad() {}

    // Constructor con todos los campos
    public Actividad(Long idActividad, String nombre, Float valoracion, Double precio, String descripcion,
                     Boolean tendencia, Boolean evento, Boolean bookmark, Boolean favoritas, Boolean unido,
                     Double latitud, Double longitud, Dificultad dificultad, String imagen, Boolean disponibilidad,
                     Deporte deporte, Set<Usuario> usuarios) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.valoracion = valoracion;
        this.precio = precio;
        this.descripcion = descripcion;
        this.tendencia = tendencia;
        this.evento = evento;
        this.bookmark = bookmark;
        this.favoritas = favoritas;
        this.unido = unido;
        this.latitud = latitud;
        this.longitud = longitud;
        this.dificultad = dificultad;
        this.imagen = imagen;
        this.disponibilidad = disponibilidad;
        this.deporte = deporte;
        this.usuarios = usuarios;
    }

    public enum Dificultad {
        facil, intermedia, dificil;
    }

    // Getters y setters
    public Long getIdActividad() { return idActividad; }
    public void setIdActividad(Long idActividad) { this.idActividad = idActividad; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Float getValoracion() { return valoracion; }
    public void setValoracion(Float valoracion) { this.valoracion = valoracion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Boolean getTendencia() { return tendencia; }
    public void setTendencia(Boolean tendencia) { this.tendencia = tendencia; }

    public Boolean getEvento() { return evento; }
    public void setEvento(Boolean evento) { this.evento = evento; }

    public Boolean getBookmark() { return bookmark; }
    public void setBookmark(Boolean bookmark) { this.bookmark = bookmark; }

    public Boolean getFavoritas() { return favoritas; }
    public void setFavoritas(Boolean favoritas) { this.favoritas = favoritas; }

    public Boolean getUnido() { return unido; }
    public void setUnido(Boolean unido) { this.unido = unido; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public Dificultad getDificultad() { return dificultad; }
    public void setDificultad(Dificultad dificultad) { this.dificultad = dificultad; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public Boolean getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(Boolean disponibilidad) { this.disponibilidad = disponibilidad; }

    public Deporte getDeporte() { return deporte; }
    public void setDeporte(Deporte deporte) { this.deporte = deporte; }

    public Set<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(Set<Usuario> usuarios) { this.usuarios = usuarios; }
}
