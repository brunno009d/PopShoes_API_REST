package com.example.backend.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.core.AbstractBaseService;
import com.example.backend.model.Calzado;
import com.example.backend.model.Imagen; 
import com.example.backend.repository.CalzadoRepository;
import com.example.backend.repository.ImagenRepository; 

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CalzadoService extends AbstractBaseService<Calzado, Integer> {

    private final CalzadoRepository calzadoRepository;
    private final ImagenRepository imagenRepository;

    public CalzadoService(CalzadoRepository calzadoRepository, ImagenRepository imagenRepository) {
        super(calzadoRepository);
        this.calzadoRepository = calzadoRepository;
        this.imagenRepository = imagenRepository;
    }

    @Override
    public Calzado crear(Calzado calzado) {
        Calzado calzadoGuardado = calzadoRepository.save(calzado);

        if (calzado.getUrlImagenInput() != null && !calzado.getUrlImagenInput().isEmpty()) {
            Imagen nuevaImagen = new Imagen();
            nuevaImagen.setTitulo("Principal - " + calzadoGuardado.getNombre());
            nuevaImagen.setUrl(calzado.getUrlImagenInput());
            nuevaImagen.setCalzado(calzadoGuardado); 
            imagenRepository.save(nuevaImagen);
        }
        return calzadoGuardado;
    }

    public Calzado partialUpdate(Calzado calzado) {
        if (calzado == null || calzado.getId() == null) {
            return null;
        }

        Calzado existente = calzadoRepository.findById(calzado.getId()).orElse(null);
        if (existente == null) {
            return null;
        }

        if (calzado.getNombre() != null) existente.setNombre(calzado.getNombre());
        if (calzado.getPrecio() != null) existente.setPrecio(calzado.getPrecio());
        if (calzado.getDescripcion() != null) existente.setDescripcion(calzado.getDescripcion());
        if (calzado.getStock() != null) existente.setStock(calzado.getStock());
        if (calzado.getMarca() != null) existente.setMarca(calzado.getMarca());
        if (calzado.getGenero() != null) existente.setGenero(calzado.getGenero());
        
  
        if (calzado.getUrlImagenInput() != null && !calzado.getUrlImagenInput().isEmpty()) {
            

            List<Imagen> imagenes = existente.getImagenes();
            
            if (imagenes != null && !imagenes.isEmpty()) {
                Imagen imgPrincipal = imagenes.get(0);
                imgPrincipal.setUrl(calzado.getUrlImagenInput());
                imagenRepository.save(imgPrincipal);
            } else {
                Imagen nuevaImagen = new Imagen();
                nuevaImagen.setTitulo("Principal - " + existente.getNombre());
                nuevaImagen.setUrl(calzado.getUrlImagenInput());
                nuevaImagen.setCalzado(existente);
                imagenRepository.save(nuevaImagen);
            }
        }
        
        return calzadoRepository.save(existente);
    }


    public List<Calzado> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return calzadoRepository.buscarPorNombreFuzzy(nombre);
    }

    public List<Calzado> buscarPorMarca(Integer marcaId) {
        if (marcaId == null) return Collections.emptyList();
        return calzadoRepository.findByMarcaId(marcaId);
    }

    public List<Calzado> buscarPorGenero(Integer generoId) {
        if (generoId == null) return Collections.emptyList();
        return calzadoRepository.findByGeneroId(generoId);
    }

    public List<Calzado> buscarPorPrecio(Long minimo, Long maximo) {
        if (minimo == null) minimo = 0L;
        if (maximo == null) maximo = Long.MAX_VALUE;
        return calzadoRepository.findByPrecioBetween(minimo, maximo);
    }

    public List<Calzado> buscarPorCategoria(Integer categoriaId) {
        if (categoriaId == null) return Collections.emptyList();
        return calzadoRepository.findByCategoriasId(categoriaId);
    }

    public List<Calzado> buscarPorEstilo(Integer estiloId) {
        if (estiloId == null) return Collections.emptyList();
        return calzadoRepository.findByEstilosId(estiloId);
    }

    public List<Calzado> buscarPorColor(Integer colorId) {
        if (colorId == null) return Collections.emptyList();
        return calzadoRepository.findByColoresId(colorId);
    }

    public List<Calzado> buscarPorTalla(Integer tallaId) {
        if (tallaId == null) return Collections.emptyList();
        return calzadoRepository.findByTallasId(tallaId);
    }

    public boolean tieneStock(Integer id, int cantidad) {
        return calzadoRepository.findById(id)
        .map(c -> c.getStock() != null && c.getStock() >= cantidad)
        .orElse(false);
    }

    public Calzado reducirStock(Integer id, int cantidad) {
        if (id == null || cantidad <= 0) return null;

        Calzado existente = calzadoRepository.findById(id).orElse(null);

        if (existente == null) return null;

        Integer stock = existente.getStock();
        if (stock == null || stock < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente");
        }

        existente.setStock(stock - cantidad);
        return calzadoRepository.save(existente);
    }

    public Calzado actualizarStock(Integer id, Integer nuevoStock) {
        if (id == null || nuevoStock == null) return null;
        Calzado existente = calzadoRepository.findById(id).orElse(null);
        if (existente == null) return null;
        existente.setStock(nuevoStock);
        return calzadoRepository.save(existente);
    }

    @Override
    public boolean eliminar(Integer id){
        Calzado calzado = calzadoRepository.findById(id).orElse(null);
        if (calzado == null) return false;

        if (calzado.getCategorias() != null) calzado.getCategorias().clear();
        if (calzado.getColores() != null) calzado.getColores().clear();
        if (calzado.getEstilos() != null) calzado.getEstilos().clear();
        if (calzado.getTallas() != null) calzado.getTallas().clear();

        calzadoRepository.save(calzado);

        calzadoRepository.delete(calzado);
        return true;
    }

    
}