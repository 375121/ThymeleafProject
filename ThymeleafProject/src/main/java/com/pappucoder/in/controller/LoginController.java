package com.pappucoder.in.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.pappucoder.in.repository.UserRepository;

@Controller
public class LoginController {
   
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/login")
    public String login() {
           
        return "login";
    }
}