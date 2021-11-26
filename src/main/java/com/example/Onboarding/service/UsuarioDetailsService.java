package com.example.Onboarding.service;

import com.example.Onboarding.entity.Role;
import com.example.Onboarding.entity.Usuario;
import com.example.Onboarding.repository.UsuarioRepositoryDao;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepositoryDao usuarioRepositoryDao;

    public UsuarioDetailsService(UsuarioRepositoryDao usuarioRepositoryDao) {
        this.usuarioRepositoryDao = usuarioRepositoryDao;
    }
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositoryDao.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado:" + usernameOrEmail));
        return new org.springframework.security.core.userdetails.User(usuario.getEmail(),
                usuario.getPassword(), mapRolesToAuthorities(usuario.getRoles()));
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
