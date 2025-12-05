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
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // --- CORRECCIÓN AQUÍ ---
    public Usuario findById(Integer id) {
        // Ya no ponemos setContrasena(null) porque @JsonProperty(WRITE_ONLY) en el modelo ya lo oculta.
        // Al quitar esa línea, evitamos borrar la contraseña accidentalmente.
        return usuarioRepository.findById(id).orElse(null);
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
        return usuarioRepository.save(usuario);
    }

    public Usuario save(Usuario usuario) {
        // Verificamos si hay contraseña nueva para hashear
        if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
            String passwordHasheada = passwordEncoder.encode(usuario.getContrasena());
            usuario.setContrasena(passwordHasheada);
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Usuario usuario) {
        return save(usuario);
    }

    // --- MÉTODO CLAVE PARA ACTUALIZAR SOLO FOTO ---
    public Usuario partialUpdate(Usuario usuarioEntrante) {
        // 1. Recuperamos el usuario REAL de la BD (con su contraseña y todo)
        Usuario usuarioDB = usuarioRepository.findById(usuarioEntrante.getId()).orElse(null);
        
        if (usuarioDB != null) {
            
            // 2. Actualizamos SOLO lo que viene de Android
            if (usuarioEntrante.getDireccion() != null) usuarioDB.setDireccion(usuarioEntrante.getDireccion());
            if (usuarioEntrante.getRun() != null) usuarioDB.setRun(usuarioEntrante.getRun());
            if (usuarioEntrante.getNombre() != null) usuarioDB.setNombre(usuarioEntrante.getNombre());
            if (usuarioEntrante.getCorreo() != null) usuarioDB.setCorreo(usuarioEntrante.getCorreo());
            
            // FOTO (Esto es lo que te interesa)
            if (usuarioEntrante.getImagenUsuario() != null) {
                usuarioDB.setImagenUsuario(usuarioEntrante.getImagenUsuario());
            }

            // OTROS CAMPOS
            if (usuarioEntrante.getApaterno() != null) usuarioDB.setApaterno(usuarioEntrante.getApaterno());
            if (usuarioEntrante.getAmaterno() != null) usuarioDB.setAmaterno(usuarioEntrante.getAmaterno());
            if (usuarioEntrante.getFechaNacimiento() != null) usuarioDB.setFechaNacimiento(usuarioEntrante.getFechaNacimiento());
            
            // CONTRASEÑA: Solo la tocamos si Android envió una NUEVA.
            // Como Android envía NULL, esto se salta y la contraseña vieja se protege.
            if (usuarioEntrante.getContrasena() != null && !usuarioEntrante.getContrasena().isEmpty()) {
                String passwordHasheada = passwordEncoder.encode(usuarioEntrante.getContrasena());
                usuarioDB.setContrasena(passwordHasheada);
            }
            
            // 3. Guardamos el usuario original modificado
            return usuarioRepository.save(usuarioDB);
        } else {
            return null;
        }
    }

    public void deleteUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }   
}