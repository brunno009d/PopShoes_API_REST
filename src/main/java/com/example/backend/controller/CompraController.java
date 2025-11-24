package com.example.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.Compra;
import com.example.backend.service.CompraService;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public ResponseEntity<List<Compra>> obtenerTodos() {
        List<Compra> list = compraService.obtenerTodos();
        if (list.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerPorId(@PathVariable Integer id) {
        Compra c = compraService.obtenerPorId(id);
        if (c == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(c);
    }

    @PostMapping
    public ResponseEntity<Compra> crear(@RequestBody Compra compra) {
        Compra creado = compraService.crear(compra);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // @PostMapping("/con-detalles")
    // public ResponseEntity<Compra> crearConDetalles(@RequestBody
    // CompraConDetalleRequest request) {
    // Compra compra = compraService.crearCompraConDetalles(request.getCompra(),
    // request.getDetalles());
    // return ResponseEntity.status(HttpStatus.CREATED).body(compra);
    // }

    @PutMapping("/{id}")
    public ResponseEntity<Compra> actualizar(@PathVariable Integer id, @RequestBody Compra compra) {
        Compra existente = compraService.obtenerPorId(id);
        if (existente == null)
            return ResponseEntity.notFound().build();
        Compra guardado = compraService.actualizar(id, compra);
        return ResponseEntity.ok(guardado);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Compra> actualizarEstado(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body 
    ) {
        String nuevoEstado = body.get("estado");
        Compra c = compraService.actualizarEstadoCompra(id, nuevoEstado);
        if (c == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Compra existente = compraService.obtenerPorId(id);
        if (existente == null)
            return ResponseEntity.notFound().build();
        boolean ok = compraService.eliminar(id);
        if (ok)
            return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
