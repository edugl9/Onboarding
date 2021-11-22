package com.example.Onboarding.repository;

import com.example.Onboarding.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UsuarioRepositoryDao extends CrudRepository<Usuario, Integer> {
}
