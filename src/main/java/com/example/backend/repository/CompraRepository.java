package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.model.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer>{
    
    List<Compra> findByUsuarioId(Integer usuarioId);
}