package com.example.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.Calzado;
import com.example.backend.service.CalzadoService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/calzados")
public class CalzadoController {
    @Autowired
    private CalzadoService calzadoService;

    @GetMapping
    public ResponseEntity<List<Calzado>> getAllCalzados(){
        List<Calzado> calzados = calzadoService.obtenerTodos();
        if (calzados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(calzados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Calzado> getCalzadoById(@PathVariable Integer id) {
        Calzado calzado = calzadoService.obtenerPorId(id);
        if (calzado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(calzado);
    }

    @PostMapping
    public ResponseEntity<Calzado> guardar(@RequestBody Calzado calzado){
        Calzado nuevoCalzado = calzadoService.crear(calzado);
        if (nuevoCalzado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(201).body(nuevoCalzado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Calzado> actualizar(@PathVariable Integer id, @RequestBody Calzado calzado){
        Calzado actCalzado = calzadoService.actualizar(id, calzado);
        if (actCalzado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actCalzado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Calzado> editar(@PathVariable Integer id,@RequestBody Calzado calzado) {
    calzado.setId(id);
    Calzado actCalzado = calzadoService.partialUpdate(calzado);

    if (actCalzado == null) {
        return ResponseEntity.notFound().build();
        }

    return ResponseEntity.ok(actCalzado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        Calzado calzado = calzadoService.obtenerPorId(id);
        if (calzado == null){
            return ResponseEntity.notFound().build();
        }
        calzadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<Calzado>> buscarPorNombre(@RequestParam String nombre) {
        List<Calzado> resultados = calzadoService.buscarPorNombre(nombre);
        return resultados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultados);
    }

    @GetMapping("/buscar/marca/{idMarca}")
    public ResponseEntity<List<Calzado>> buscarPorMarca(@PathVariable Integer idMarca) {
        List<Calzado> resultados = calzadoService.buscarPorMarca(idMarca);
        return resultados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultados);
    }

    @GetMapping("/buscar/genero/{idGenero}")
    public ResponseEntity<List<Calzado>> buscarPorGenero(@PathVariable Integer idGenero) {
        List<Calzado> resultados = calzadoService.buscarPorGenero(idGenero);
        return resultados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultados);
    }

    @GetMapping("/buscar/precio")
    public ResponseEntity<List<Calzado>> buscarPorPrecio(
            @RequestParam(required = false) Long min,
            @RequestParam(required = false) Long max) {

        List<Calzado> resultados = calzadoService.buscarPorPrecio(min, max);
        return resultados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultados);
    }

    @GetMapping("/buscar/estilo/{idEstilo}")
    public ResponseEntity<List<Calzado>> buscarPorEstilo(@PathVariable Integer idEstilo) {
        List<Calzado> resultados = calzadoService.buscarPorEstilo(idEstilo);
        return resultados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultados);
    }

    @GetMapping("/buscar/color/{idColor}")
    public ResponseEntity<List<Calzado>> buscarPorColor(@PathVariable Integer idColor) {
        List<Calzado> resultados = calzadoService.buscarPorColor(idColor);
        return resultados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultados);
    }

    @GetMapping("/buscar/categoria/{idCategoria}")
    public ResponseEntity<List<Calzado>> buscarPorCategoria(@PathVariable Integer idCategoria) {
        List<Calzado> resultados = calzadoService.buscarPorCategoria(idCategoria);
        return resultados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultados);
    }

    @GetMapping("/buscar/talla/{idTalla}")
    public ResponseEntity<List<Calzado>> buscarPorTalla(@PathVariable Integer idTalla) {
        List<Calzado> resultados = calzadoService.buscarPorTalla(idTalla);
        return resultados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultados);
    }
}
