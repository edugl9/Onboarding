package com.example.Onboarding.repository;

import com.example.Onboarding.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface AuthRepository extends CrudRepository<Usuario, Long> {
}
