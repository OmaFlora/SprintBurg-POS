package com.sprintburg.pos.repository;

import com.sprintburg.pos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Busca y retorna una lista de productos basados en su 'type'.
     * Útil para filtrar en la interfaz de pedidos (ej. mostrar solo 'BURGER_BASE').
     * @param type El tipo de producto (ej. "BURGER_BASE", "TOPPING", "DRINK").
     * @return Una lista de productos del tipo especificado.
     */
    List<Product> findByType(String type);

    /**
     * Busca productos cuyo inventario ('stock') sea mayor al valor especificado.
     * Útil para mostrar solo productos disponibles en la pantalla de Pedidos.
     * @param stock Cantidad mínima de stock (usualmente 0).
     * @return Lista de productos con stock suficiente.
     */
    List<Product> findByStockGreaterThan(Integer stock);

    /**
     * Combina la búsqueda por 'type' y por stock disponible.
     * @param type Tipo de producto.
     * @param stock Cantidad mínima de stock.
     * @return Lista de productos disponibles de un tipo específico.
     */
    List<Product> findByTypeAndStockGreaterThan(String type, Integer stock);

    /**
     * Busca productos cuyo nombre contenga la cadena de texto, sin distinguir mayúsculas/minúsculas.
     * Útil para la función de búsqueda en el Menú de Administración.
     * @param name Nombre o parte del nombre a buscar.
     * @return Lista de productos coincidentes.
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Busca productos que están activos (isActive = true).
     * Útil para el menú de pedidos, excluyendo ítems que están temporalmente agotados.
     * @param isActive Debe ser 'true'.
     * @return Lista de productos activos.
     */
    List<Product> findByIsActive(Boolean isActive);

    /**
     * Combina la búsqueda por tipo y estado activo.
     * @param type Tipo de producto.
     * @param isActive Estado de actividad.
     * @return Lista de productos activos de un tipo específico.
     */
    List<Product> findByTypeAndIsActive(String type, Boolean isActive);
}