package com.sprintburg.pos.controller;

import com.sprintburg.pos.model.Product;
import com.sprintburg.pos.model.User;
import com.sprintburg.pos.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('MANAGER')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = adminService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(adminService.getAllProducts());
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedDetails) {
        Product updatedProduct = adminService.updateProduct(productId, updatedDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/products/{productId}/stock")
    public ResponseEntity<Product> adjustStock(@PathVariable Long productId, @RequestBody Integer quantityChange) {
        Product adjustedProduct = adminService.adjustStock(productId, quantityChange);
        return ResponseEntity.ok(adjustedProduct);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = adminService.createUser(user);
        // NOTA: Es buena práctica no retornar la contraseña cifrada en la respuesta.
        createdUser.setPassword(null);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllActiveUsers() {
        return ResponseEntity.ok(adminService.getAllActiveUsers());
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedDetails) {
        User updatedUser = adminService.updateUser(userId, updatedDetails);
        updatedUser.setPassword(null);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/users/{userId}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId) {
        adminService.deactivateUser(userId);
        return ResponseEntity.noContent().build(); // 204 No Content para operaciones sin retorno de cuerpo
    }
}