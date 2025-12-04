package com.sprintburg.pos.config;

import com.sprintburg.pos.model.Product;
import com.sprintburg.pos.model.User;
import com.sprintburg.pos.model.enums.Role;
import com.sprintburg.pos.repository.ProductRepository;
import com.sprintburg.pos.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                      ProductRepository productRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setFullName("Administrador del Sistema");
                admin.setRole(Role.MANAGER);
                admin.setActive(true);
                admin.setDateCreated(LocalDateTime.now());
                userRepository.save(admin);
                System.out.println("✅ Usuario Admin creado.");
            }

            if (productRepository.count() == 0) {

                Product p1 = new Product();
                p1.setName("Hamburguesa Clásica");
                p1.setPrice(new BigDecimal("55.0"));
                p1.setCost(new BigDecimal("55.0"));
                p1.setStock(100);
                p1.setType("BURGER_BASE");
                p1.setActive(true);

                Product p2 = new Product();
                p2.setName("Hamburguesa Doble Queso");
                p2.setPrice(new BigDecimal("75.0"));
                p2.setCost(new BigDecimal("75.0"));
                p2.setStock(80);
                p2.setType("BURGER_BASE");
                p2.setActive(true);

                Product p3 = new Product();
                p3.setName("Papas Fritas Grandes");
                p3.setPrice(new BigDecimal("45.0"));
                p3.setCost(new BigDecimal("45.0"));
                p3.setStock(200);
                p3.setType("SIDE");
                p3.setActive(true);

                Product p4 = new Product();
                p4.setName("Refresco Cola");
                p4.setPrice(new BigDecimal("20.0"));
                p4.setCost(new BigDecimal("20.0"));
                p4.setStock(150);
                p4.setType("DRINK");
                p4.setActive(true);

                Product p5 = new Product();
                p5.setName("Tocino Extra");
                p5.setPrice(new BigDecimal("20.00"));
                p5.setCost(new BigDecimal("20.0"));
                p5.setStock(50);
                p5.setType("TOPPING");
                p5.setActive(true);

                productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

                System.out.println("✅ Productos de prueba insertados en la base de datos.");
            }
        };
    }
}