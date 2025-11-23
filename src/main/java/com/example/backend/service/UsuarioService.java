package com.example.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.model.Usuario;
import com.example.backend.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    public Usuario findById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null){
            usuario.setContrasena(null);
        }
        return usuario;
    }

    public Usuario login(Usuario usuario) {
        Usuario foundUsuario = usuarioRepository.findByCorreo(usuario.getCorreo());

        if(foundUsuario != null && passwordEncoder.matches(usuario.getContrasena(), foundUsuario.getContrasena())) {
            return foundUsuario;
        } else {
            return null;
        }

    }

    public Usuario registro(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está en uso.");
        }

        if (usuarioRepository.existsByNombre(usuario.getNombre())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }

        String passwordHasheada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(passwordHasheada);

        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        // Para seguridad, no devolver la contraseña hasheada
        nuevoUsuario.setContrasena(null);

        return nuevoUsuario;
    }

    public Usuario save(Usuario usuario) {
        String passwordHasheada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(passwordHasheada);
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Usuario usuario) {
        return save(usuario);
    }

    public Usuario partialUpdate(Usuario usuario) {
        Usuario existingUsuario = usuarioRepository.findById(usuario.getId()).orElse(null);
        if (existingUsuario != null) {
            if (usuario.getRun() != null) {
                existingUsuario.setRun(usuario.getRun());
            }
            if (usuario.getNombre() != null) {
                existingUsuario.setNombre(usuario.getNombre());
            }
            if (usuario.getCorreo() != null) {
                existingUsuario.setCorreo(usuario.getCorreo());
            }
            if (usuario.getContrasena() != null) {
                String passwordHasheada = passwordEncoder.encode(usuario.getContrasena());
                existingUsuario.setContrasena(passwordHasheada);
            }
            if (usuario.getApaterno() != null) {
                existingUsuario.setApaterno(usuario.getApaterno());
            }
            if (usuario.getAmaterno() != null) {
                existingUsuario.setAmaterno(usuario.getAmaterno());
            }
            if (usuario.getFechaNacimiento() != null) {
                existingUsuario.setFechaNacimiento(usuario.getFechaNacimiento());
            }
            if (usuario.getFechaCreacion() != null) {
                existingUsuario.setFechaCreacion(usuario.getFechaCreacion());
            }
            if (usuario.getRol() != null) {
                existingUsuario.setRol(usuario.getRol());
            }

            return usuarioRepository.save(existingUsuario);
        } else {
            return null;
        }
    }

    //revision
    public void deleteUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }  

}
