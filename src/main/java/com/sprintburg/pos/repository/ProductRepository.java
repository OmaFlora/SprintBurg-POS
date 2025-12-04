package com.sprintburg.pos.repository;

import com.sprintburg.pos.model.Product;
import com.sprintburg.pos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Busca productos que están activos (isActive = true).
     * Útil para el menú de pedidos, excluyendo ítems que están temporalmente agotados.
     * @param isActive Debe ser 'true'.
     * @return Lista de productos activos.
     */
    List<Product> findByIsActive(Boolean isActive);

    /**
     * Busca productos cuyo nombre contenga la cadena de texto, sin distinguir mayúsculas/minúsculas.
     * Útil para la función de búsqueda en el Menú de Administración.
     * @param name Nombre o parte del nombre a buscar.
     * @return Lista de productos coincidentes.
     */
    List<Product> findByNameContainingIgnoreCase(String name);
}