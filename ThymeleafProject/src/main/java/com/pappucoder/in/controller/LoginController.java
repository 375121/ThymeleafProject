package com.pappucoder.in.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pappucoder.in.bean.Employee;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    // Fake employee data (in real app fetch from DB)
    private static final Map<String, Employee> employees = new HashMap<>();
    static {
        employees.put("E101", new Employee("E101", "Ajay", "123"));
        employees.put("E102", new Employee("E102", "John Doe", "welcome"));
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("error", false);
        return "login";
    }

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestParam String username,
                                   @RequestParam String password,
                                   HttpSession session,
                                   Model model) {

        Employee emp = employees.get(username);

        if (emp != null && emp.getPassword().equals(password)) {
            // ✅ Store user info in session
            session.setAttribute("loggedInUser", emp);
            session.setMaxInactiveInterval(600); // 10 minutes
            return "redirect:/portal";
        } else {
            model.addAttribute("error", true);
            return "login"; // reload login page with error
        }
    }

    @GetMapping("/portal")
    public String showPortal(HttpSession session, Model model) {
        Employee emp = (Employee) session.getAttribute("loggedInUser");
        if (emp == null) {
            return "redirect:/login";
        }
        model.addAttribute("employee", emp);
        return "portal"; // thymeleaf page
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
