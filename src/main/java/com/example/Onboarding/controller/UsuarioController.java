package com.example.Onboarding.controller;

import com.example.Onboarding.entity.Usuario;
import com.example.Onboarding.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> nuevoUsuario(@RequestBody Usuario usuario){
        usuarioService.nuevoUsuario(usuario);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/{idusuario}")
    public Optional<Usuario> getUsuarioPorId(@PathVariable("idusuario") Integer idusuario){
        return usuarioService.buscarUsuarioPorId(idusuario);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUsuario(@RequestBody Usuario usuario){
        if (usuarioService.buscarUsuarioPorId(usuario.getIdusuario()).isEmpty()){
            //throw new InexistenteException("Alumno no encontrado");
        }
        usuarioService.updateUsuario(usuario);
        return ResponseEntity.ok("Actualizacion exitosa");
    }

    @DeleteMapping("/delete/{idusuario}")
    public ResponseEntity<String> deleteUsuario(@PathVariable("idusuario") Integer idusuario){
        usuarioService.deleteUsuario(idusuario);
        return ResponseEntity.ok("Se elimio usuario");
    }

}
