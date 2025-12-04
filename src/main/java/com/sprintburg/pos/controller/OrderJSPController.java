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

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/orders")
    public String showOrderMenu(Model model) {
        List<Product> availableProducts = productRepository.findByIsActive(true);

        model.addAttribute("products", availableProducts);

        return "order";
    }

}