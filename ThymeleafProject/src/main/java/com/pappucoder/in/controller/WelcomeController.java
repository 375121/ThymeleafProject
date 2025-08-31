package com.pappucoder.in.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/get")
    public String welcomePage(Model model) {
        model.addAttribute("message", "Welcome to Spring Boot with Thymeleaf!");
        return "welcome"; 
    }
    
    @GetMapping("/thank")
    public String thankPage(Model model) {
        model.addAttribute("thankMessage", "Thank You for Visiting!");
        return "thank"; // renders thank.html
    }
}