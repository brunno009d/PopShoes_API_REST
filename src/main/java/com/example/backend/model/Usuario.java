package com.example.backend.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "run", nullable = false, length = 12)
    private String run;

    @Column(name = "contrasena", nullable = false, length = 100)
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private String contrasena;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", nullable = false, length = 100)
    private String correo;

    @Column(name = "a_paterno", nullable = false, length = 100)
    private String apaterno;

    @Column(name = "a_materno", nullable = false, length = 100)
    private String amaterno;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    @Column(name = "fecha_creacion", nullable = false)
    private Date fechaCreacion;

    //campo de imagen 
    @Column(name = "imagen_usuario", nullable=true, length=500)
    private String imagenUsuario;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @ManyToMany(mappedBy = "usuarios")
    @JsonIgnore
    private List<Direccion> direcciones;
}