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
import com.pappucoder.in.service.EmailService;
import com.pappucoder.in.service.OtpService;


@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Autowired
	private OtpService otpService;
	@Autowired
	private EmailService emailService;

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
			@RequestParam("password") String rawPassword,
			@RequestParam("email") String email,
			RedirectAttributes redirectAttributes, Model model) {

		// Check if username already exists
		if (userRepository.findByUsername(username).isPresent()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Username '"+username+"' already exists!");
			return "redirect:/usercreate?error=username_exists";
		}

		// Generate and send OTP
		String otp = otpService.generateOtp(username);
	try {
		emailService.sendOtpEmail(email, otp);
		} catch (Exception e) {
			System.out.println("Error sending email: " + e.getMessage());
		}
		model.addAttribute("username", username);
		model.addAttribute("password", rawPassword);
		model.addAttribute("email", email);
		return "otp";
	}

	@GetMapping("/otp")
	public String showOtpPage(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "email", required = false) String email,
			Model model) {
		if (username != null) model.addAttribute("username", username);
		if (password != null) model.addAttribute("password", password);
		if (email != null) model.addAttribute("email", email);
		return "otp";
	}

	@PostMapping("/validate-otp")
	public String validateOtp(@RequestParam("username") String username,
			@RequestParam("password") String rawPassword,
			@RequestParam("email") String email,
			@RequestParam("otp") String otp,
			RedirectAttributes redirectAttributes) {
		if(true) {
		
		if (otpService.validateOtp(username, otp)) {
			otpService.clearOtp(username);
			String encodedPassword = passwordEncoder.encode(rawPassword);
			UserEntity user = new UserEntity();
			user.setUsername(username);
			user.setPassword(encodedPassword);
			user.setRole("USER");
			user.setEmail(email);
			userRepository.save(user);
			redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
			return "redirect:/login?success";
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Invalid OTP. Please try again.");
			redirectAttributes.addFlashAttribute("username", username);
			redirectAttributes.addFlashAttribute("password", rawPassword);
			redirectAttributes.addFlashAttribute("email", email);
			return "redirect:/otp?error=invalid_otp";
		}
		} else {
			
			String encodedPassword = passwordEncoder.encode(rawPassword);
			UserEntity user = new UserEntity();
			user.setUsername(username);
			user.setPassword(encodedPassword);
			user.setRole("USER");
			user.setEmail(email);
			userRepository.save(user);
			redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
			return "redirect:/login?success";
		}
	}
}