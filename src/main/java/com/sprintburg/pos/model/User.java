package com.sprintburg.pos.model;

import com.sprintburg.pos.model.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pos_user")
public class User {

    /**
     * Identificador único del empleado (clave primaria).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario o código de empleado para iniciar sesión.
     * Debe ser único.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Contraseña cifrada del empleado.
     * Crucial: NUNCA guardar contraseñas sin cifrar (se usará Spring Security).
     */
    @Column(nullable = false)
    private String password;

    /**
     * Nombre completo del empleado (para auditoría de ventas).
     */
    private String fullName;

    /**
     * Rol del usuario para la autorización (Gerente o Empleado).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * Indica si la cuenta está activa (ej. para empleados despedidos).
     */
    private Boolean isActive = true;

    /**
     * Fecha de creación del usuario.
     */
    private LocalDateTime dateCreated = LocalDateTime.now();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}