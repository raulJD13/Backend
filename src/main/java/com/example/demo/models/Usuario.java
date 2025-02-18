package com.example.demo.models;

import jakarta.persistence.*;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false)
    @Email(message = "Debe ingresar un email válido")
    private String email;

    @Column(name = "localizacion")
    private String localizacion;

    @Column(nullable = false)
    private String contraseña;

    @Column
    private String fotoPerfil;

    @Column
    private String fotoFondoPerfil;

    @Column(nullable = false)
    private String token;

    // Relación muchos a muchos con Actividad
    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "usuarioactividad", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "usuario_id"), // Llave foránea hacia Usuario
        inverseJoinColumns = @JoinColumn(name = "actividad_id") // Llave foránea hacia Actividad
    )
    private Set<Actividad> actividades;

    // Constructores, getters y setters

    public Usuario() {
    }

    public Usuario(String email, String localizacion, String contraseña, String fotoPerfil, String fotoFondoPerfil, String token) {
        this.email = email;
        this.localizacion = localizacion;
        this.contraseña = contraseña;
        this.fotoPerfil = fotoPerfil;
        this.fotoFondoPerfil = fotoFondoPerfil;
        this.token = token;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getFotoFondoPerfil() {
        return fotoFondoPerfil;
    }

    public void setFotoFondoPerfil(String fotoFondoPerfil) {
        this.fotoFondoPerfil = fotoFondoPerfil;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(Set<Actividad> actividades) {
        this.actividades = actividades;
    }
}
