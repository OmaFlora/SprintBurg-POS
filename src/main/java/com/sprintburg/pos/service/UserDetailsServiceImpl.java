package com.sprintburg.pos.service;

import com.sprintburg.pos.model.User;
import com.sprintburg.pos.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority; // Importación necesaria
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Importación CRUCIAL
import org.springframework.stereotype.Service;

import java.util.Collections; // Importación para Collections
import java.util.Collection;  // Importación para Collection

/**
 * Servicio central para cargar los detalles del usuario durante el proceso de autenticación.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username));

        if (!user.getActive()) {
            throw new UsernameNotFoundException("La cuenta del usuario " + username + " está inactiva.");
        }

        // --- 1. Obtener las Autoridades (Roles) ---
        // Spring Security requiere que los roles estén precedidos por el prefijo "ROLE_"
        // y envueltos en objetos SimpleGrantedAuthority.
        Collection<? extends GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );

        // --- 2. Construir UserDetails de Spring Security ---
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // Contraseña cifrada (BCrypt)
                authorities         // Roles con el formato "ROLE_MANAGER" o "ROLE_EMPLOYEE"
        );
    }
}