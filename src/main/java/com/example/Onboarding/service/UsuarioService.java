package com.example.Onboarding.service;

import com.example.Onboarding.entity.Usuario;
import com.example.Onboarding.repository.AuthRepository;
import com.example.Onboarding.repository.UsuarioRepositoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositoryDao usuarioRepositoryDao;

    public void nuevoUsuario(Usuario usuario) {
        usuarioRepositoryDao.save(usuario);
    }

    public Optional<Usuario> buscarUsuarioPorId(Integer id) {
        return usuarioRepositoryDao.findById(id);
    }

    public Usuario updateUsuario(Usuario id) {
        return usuarioRepositoryDao.save(id);
    }

    public void deleteUsuario(Integer idusuario) {
        usuarioRepositoryDao.deleteById(idusuario);
    }

    public List<Usuario> getUsuarios() {
        return (List<Usuario>) usuarioRepositoryDao.findAll();
    }

    public Boolean existsByTelefono(String telefono){
        return usuarioRepositoryDao.existsByTelefono(telefono);
    }
}
