package com.example.Onboarding.controller;

import com.example.Onboarding.entity.Usuario;
import com.example.Onboarding.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> nuevoUsuario(@RequestBody Usuario usuario){
        List<Usuario> usuarioList = usuarioService.getUsuarios();
        for (Usuario usuarios:usuarioList) {
            if (usuarios.getEmail().equals(usuario.getEmail()) || usuario.getEdad()<18 || !usuario.getEmail().contains("@") || usuario.getTelefono().length()!=10 || !usuario.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$")) {
                return ResponseEntity.ok("El usuario no se puede crear");
            }
        }
        usuarioService.nuevoUsuario(usuario);
        return ResponseEntity.ok("Usuario creado");
    }

    @GetMapping("/{idusuario}")
    public Optional<Usuario> getUsuarioPorId(@PathVariable("idusuario") Integer idusuario){
        return usuarioService.buscarUsuarioPorId(idusuario);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUsuario(@RequestBody Usuario usuario){
        //if (usuarioService.buscarUsuarioPorId(usuario.getIdusuario()).isEmpty()){
            //throw new InexistenteException("Alumno no encontrado");
        //}
        usuarioService.updateUsuario(usuario);
        return ResponseEntity.ok("Actualizacion exitosa");
    }

    @DeleteMapping("/delete/{idusuario}")
    public ResponseEntity<String> deleteUsuario(@PathVariable("idusuario") Integer idusuario){
        usuarioService.deleteUsuario(idusuario);
        return ResponseEntity.ok("Se elimio usuario");
    }



}
