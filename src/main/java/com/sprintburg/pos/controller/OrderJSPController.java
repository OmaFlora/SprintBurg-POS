package com.sprintburg.pos.controller;

import com.sprintburg.pos.model.Product;
import com.sprintburg.pos.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class OrderJSPController {

    private final ProductRepository productRepository;

    public OrderJSPController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Mapea a la URL raíz y pide al usuario que inicie sesión.
     */
    @GetMapping("/login")
    public String showLoginForm() {
        // Devuelve la vista /WEB-INF/views/login.jsp
        return "login";
    }

    /**
     * Mapea al menú de pedidos y carga los productos iniciales.
     */
    @GetMapping("/orders")
    // Se requiere autenticación y autorización (configurada en WebSecurityConfig)
    public String showOrderMenu(Model model) {
        // Carga los productos activos para mostrar en el menú de pedidos
        List<Product> availableProducts = productRepository.findByIsActive(true);

        // Añade la lista al objeto Model, que JSP puede acceder
        model.addAttribute("products", availableProducts);

        // Devuelve la vista /WEB-INF/views/order.jsp
        return "order";
    }

    // Aquí iría otro @Controller para la vista de Administración
}