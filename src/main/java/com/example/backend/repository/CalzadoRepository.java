package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.Calzado;


@Repository
public interface CalzadoRepository extends JpaRepository<Calzado, Integer>{
    
    // Query difusa (buscar por nombre)
    @Query(value = "SELECT * FROM calzado WHERE SIMILARITY(nombre, :nombre) > 0.1 ORDER BY SIMILARITY(nombre, :nombre) DESC", nativeQuery = true)
    List<Calzado> buscarPorNombreFuzzy(@Param("nombre") String nombre);

    List<Calzado> findByMarcaId(Integer marcaId);

    List<Calzado> findByGeneroId(Integer generoId);

    List<Calzado> findByPrecioBetween(Long precioMin, Long precioMax);

    List<Calzado> findByEstilosId(Integer estiloId);

    List<Calzado> findByCategoriasId(Integer categoriaId);
    
    List<Calzado> findByColoresId(Integer colorId);
    
    List<Calzado> findByTallasId(Integer tallaId);
}
    
