package com.example.demo.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, unique = true)
    private String email;

    private String localizacion;

    @Column(nullable = false)
    private String contraseña;

    private String fotoPerfil;
    private String fotoFondoPerfil;

    @Column(nullable = false)
    private String token;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();



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

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
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

}
