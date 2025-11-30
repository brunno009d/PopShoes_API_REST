package com.example.backend.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.model.Compra;
import com.example.backend.model.DetalleCompra;
import com.example.backend.model.MetodoEnvio;
import com.example.backend.model.MetodoPago;
import com.example.backend.repository.CompraRepository;
import com.example.backend.repository.MetodoEnvioRepository;
import com.example.backend.repository.MetodoPagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private MetodoEnvioRepository metodoEnvioRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public List<Compra> obtenerTodos() {
        return compraRepository.findAll();
    }

    public Compra obtenerPorId(Integer id) {
        return compraRepository.findById(id).orElse(null);
    }

    public Compra crear(Compra compra) {
        compra.setFecha(new Date());
        if (compra.getEstado() == null) {
            compra.setEstado("Pendiente");
        }

        if (compra.getEnvioNombre() != null) {
            MetodoEnvio envio = metodoEnvioRepository.findByNombre(compra.getEnvioNombre());
            if (envio != null) {
                compra.setMetodoEnvio(envio);
            }
        }

        if (compra.getPagoNombre() != null) {
            MetodoPago pago = metodoPagoRepository.findByNombre(compra.getPagoNombre());
            if (pago != null) {
                compra.setMetodoPago(pago);
            }
        }

        if (compra.getDetalles() != null) {
            for (DetalleCompra detalle : compra.getDetalles()) {
                detalle.setCompra(compra);
            }
        }
        return compraRepository.save(compra);
    }

    public Compra actualizar(Integer id, Compra compra) {
        return compraRepository.save(compra);
    }

    public Compra actualizarEstadoCompra(Integer id, String nuevoEstado) {
        Compra c = obtenerPorId(id);
        if (c != null) {
            c.setEstado(nuevoEstado); 
            return compraRepository.save(c);
        }
        return null;
    }

    public boolean eliminar(Integer id) {
        if (compraRepository.existsById(id)) {
            compraRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Compra> buscarPorUsuarioId(Integer userId) {
        if (userId == null) return Collections.emptyList();
        return compraRepository.findByUsuarioId(userId);
    }
}