package com.example.backend.model;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total", nullable = false)
    private Long total;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "direccion_envio", length = 255)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_metodo_envio")
    private MetodoEnvio metodoEnvio;

    @ManyToOne
    @JoinColumn(name = "id_metodo_pago")
    private MetodoPago metodoPago;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("compra")
    private List<DetalleCompra> detalles;
    
    @Transient
    private String envioNombre;

    @Transient
    private String pagoNombre;
}