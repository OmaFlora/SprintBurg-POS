package com.sprintburg.pos.service;

import com.sprintburg.pos.model.Product;
import com.sprintburg.pos.model.User;
import com.sprintburg.pos.model.enums.Role;
import com.sprintburg.pos.repository.ProductRepository;
import com.sprintburg.pos.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya existe: " + user.getUsername());
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (user.getRole() == null) {
            user.setRole(Role.EMPLOYEE);
        }

        return userRepository.save(user);
    }

    public List<User> getAllActiveUsers() {
        return userRepository.findByIsActive(true);
    }

    @Transactional
    public User updateUser(Long userId, User updatedDetails) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + userId));

        existingUser.setFullName(updatedDetails.getFullName());
        existingUser.setRole(updatedDetails.getRole());
        existingUser.setActive(updatedDetails.getActive());

        if (updatedDetails.getPassword() != null && !updatedDetails.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(updatedDetails.getPassword());
            existingUser.setPassword(encodedPassword);
        }

        return userRepository.save(existingUser);
    }

    @Transactional
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + userId));
        user.setActive(false);
        userRepository.save(user);
    }

    @Transactional
    public Product createProduct(Product product) {
        if (productRepository.findByNameContainingIgnoreCase(product.getName()).stream().count() > 0) {
            throw new IllegalArgumentException("Ya existe un producto con el nombre: " + product.getName());
        }
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product updateProduct(Long productId, Product updatedDetails) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + productId));

        existingProduct.setName(updatedDetails.getName());
        existingProduct.setPrice(updatedDetails.getPrice());
        existingProduct.setCost(updatedDetails.getCost());
        existingProduct.setType(updatedDetails.getType());
        existingProduct.setActive(updatedDetails.getActive());

        return productRepository.save(existingProduct);
    }

    @Transactional
    public Product adjustStock(Long productId, Integer quantityChange) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + productId));
        Integer newStock = (product.getStock() != null ? product.getStock() : 0) + quantityChange;
        if (newStock < 0) {
            throw new IllegalArgumentException("El ajuste resultaría en stock negativo para: " + product.getName());
        }
        product.setStock(newStock);
        return productRepository.save(product);
    }


}