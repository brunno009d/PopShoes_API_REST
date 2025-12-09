package com.example.backend.service;

import org.springframework.stereotype.Service;
import com.example.backend.core.AbstractBaseService;
import com.example.backend.model.UsuarioEstilo;
import com.example.backend.repository.UsuarioEstiloRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioEstiloService extends AbstractBaseService<UsuarioEstilo, Integer> {

    public UsuarioEstiloService(UsuarioEstiloRepository usuarioEstiloRepository) {
        super(usuarioEstiloRepository);
    }
}
