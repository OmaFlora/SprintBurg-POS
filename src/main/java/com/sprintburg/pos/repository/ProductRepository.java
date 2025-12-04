package com.sprintburg.pos.repository;

import com.sprintburg.pos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByType(String type);

    List<Product> findByStockGreaterThan(Integer stock);

    List<Product> findByTypeAndStockGreaterThan(String type, Integer stock);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByIsActive(Boolean isActive);

    List<Product> findByTypeAndIsActive(String type, Boolean isActive);
}