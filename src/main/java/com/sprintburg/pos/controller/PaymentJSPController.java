package com.sprintburg.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class PaymentJSPController {

    @GetMapping("/payment")
    public String showPaymentPage(@RequestParam String total,
                                  @RequestParam String subtotal,
                                  @RequestParam String taxes,
                                  Model model) {

        model.addAttribute("total", new BigDecimal(total));
        model.addAttribute("subtotal", new BigDecimal(subtotal));
        model.addAttribute("taxes", new BigDecimal(taxes));

        return "payment";
    }
}