package com.example.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

import com.example.backend.model.Usuario;
import com.example.backend.service.UsuarioService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuario(){
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Usuario login = usuarioService.login(usuario);
        
        if (login != null) {
            login.setContrasena(null);
            return ResponseEntity.ok(login);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        usuario.setId(null);
        Usuario usuarioNew = usuarioService.save(usuario);
        return ResponseEntity.status(201).body(usuarioNew);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario updatedUsuario = usuarioService.save(usuario);
        if (updatedUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUsuario);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> updateParcialUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario updatedUsuario = usuarioService.partialUpdate(usuario);
        if (updatedUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUsuario);
    }

    @PatchMapping("/{id}/foto")
    public ResponseEntity<?> actualizarFotoPerfil(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        
        // 1. Buscamos al usuario por su ID
        Usuario usuario = usuarioService.findById(id); 
        
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. Extraemos la foto del JSON que envía Android
        // Se espera un JSON así: { "imagenUsuario": "base64....." }
        if (body.containsKey("imagenUsuario")) {
            String nuevaFoto = body.get("imagenUsuario");
            
            // 3. Modificamos SOLO el campo de la imagen
            usuario.setImagenUsuario(nuevaFoto);
            
            // 4. Guardamos. Como el objeto 'usuario' ya tiene el nombre/apellido cargados de la base de datos,
            // no darán error de NULL.
            usuarioService.save(usuario);
            
            return ResponseEntity.ok(usuario); // Devolvemos el usuario actualizado
        } else {
            return ResponseEntity.badRequest().body("Falta el campo 'imagenUsuario'");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();  
    } 
}
