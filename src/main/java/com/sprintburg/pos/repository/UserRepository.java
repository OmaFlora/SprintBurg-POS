package com.sprintburg.pos.repository;

import com.sprintburg.pos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import com.sprintburg.pos.model.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findByRole(Role role);

    List<User> findByIsActive(Boolean isActive);

    List<User> findByRoleAndIsActive(Role role, Boolean isActive);

    List<User> findByFullNameContainingIgnoreCase(String fullName);
}