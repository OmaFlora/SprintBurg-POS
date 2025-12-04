package com.sprintburg.pos.controller;

import com.sprintburg.pos.model.Product;
import com.sprintburg.pos.model.User;
import com.sprintburg.pos.model.enums.Role;
import com.sprintburg.pos.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminJSPController {

    private final AdminService adminService;

    public AdminJSPController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/products")
    public String showProductManagement(Model model) {
        model.addAttribute("products", adminService.getAllProducts());
        model.addAttribute("newProduct", new Product());
        return "admin-products";
    }

    @PostMapping("/products/create")
    public String createProduct(Product product) {
        try {
            adminService.createProduct(product);
            return "redirect:/admin/products?success";
        } catch (Exception e) {
            return "redirect:/admin/products?error=" + e.getMessage();
        }
    }

    @GetMapping("/users")
    public String showUserManagement(Model model) {
        model.addAttribute("users", adminService.getAllActiveUsers());
        return "admin-users";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user, @RequestParam String roleName, Model model) {
        try {
            user.setRole(Role.valueOf(roleName));
            adminService.createUser(user);
            return "redirect:/admin/register?success";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            return "register";
        }
    }
}