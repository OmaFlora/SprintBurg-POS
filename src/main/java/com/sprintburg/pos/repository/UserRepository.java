package com.sprintburg.pos.repository;

import com.sprintburg.pos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import com.sprintburg.pos.model.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 1. Consulta de Autenticación: Buscar un usuario por su nombre de usuario.
     * Este es el método más CRÍTICO para Spring Security, ya que busca el usuario
     * al intentar iniciar sesión.
     * @param username Nombre de usuario único del empleado.
     * @return Un objeto Optional que contiene el User si existe.
     */
    Optional<User> findByUsername(String username);

    /**
     * 2. Consulta de Administración: Buscar todos los usuarios por su rol.
     * Útil para listar a todos los 'Gerentes' o todos los 'Empleados'.
     * @param role El rol del usuario (Role.MANAGER o Role.EMPLOYEE).
     * @return Lista de usuarios con el rol especificado.
     */
    List<User> findByRole(Role role);

    /**
     * 3. Consulta de Estado: Buscar usuarios que están actualmente activos en el sistema.
     * @param isActive Debe ser 'true'.
     * @return Lista de usuarios activos.
     */
    List<User> findByIsActive(Boolean isActive);

    /**
     * 4. Consulta Combinada: Buscar usuarios por rol y estado activo.
     * Útil para listar solo a los empleados activos.
     */
    List<User> findByRoleAndIsActive(Role role, Boolean isActive);

    /**
     * 5. Consulta por Nombre Completo: Buscar empleados por su nombre completo.
     * Útil para la búsqueda rápida en el Menú de Administración.
     */
    List<User> findByFullNameContainingIgnoreCase(String fullName);

    // Métodos heredados:
    // - save(User entity): Para crear o actualizar un empleado.
    // - findById(Long id): Para obtener los detalles de un empleado.
    // - findAll(): Para obtener la lista completa de empleados.
}