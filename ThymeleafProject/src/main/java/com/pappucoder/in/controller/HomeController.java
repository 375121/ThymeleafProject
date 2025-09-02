package com.pappucoder.in.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pappucoder.in.model.UserEntity;
import com.pappucoder.in.repository.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public PasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/userinfo")
	public String userInfo(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		return "userinfo";
	}

	@GetMapping("/usercreate")
	public String userCreate() {
		return "usercreate";
	}

	@PostMapping("/usercreate")
	public String handleUserCreate(@RequestParam("username") String username,
			@RequestParam("password") String rawPassword, RedirectAttributes redirectAttributes) {

		// Check if username already exists
		if (userRepository.findByUsername(username).isPresent()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Username already exists!");
			return "redirect:/usercreate?error=username_exists";
		}

		String encodedPassword = passwordEncoder.encode(rawPassword);
		UserEntity user = new UserEntity();
		user.setUsername(username);
		user.setPassword(encodedPassword);
		user.setRole("USER");
		userRepository.save(user);

		redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
		return "redirect:/?success";
	}
}