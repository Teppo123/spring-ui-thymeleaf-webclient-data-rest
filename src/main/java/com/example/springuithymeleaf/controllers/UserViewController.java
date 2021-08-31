package com.example.springuithymeleaf.controllers;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springuithymeleaf.dto.UserDTO;
import com.example.springuithymeleaf.services.UserService;

import reactor.core.publisher.Mono;

@Controller
public class UserViewController {

	@Autowired
	private UserService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserViewController.class);

	@GetMapping({ "", "/index" })
	public Mono<String> showUserList(Model model) {
		LOGGER.info("entered /index");
		model.addAttribute("newUser", new UserDTO());
		return this.userService.getUsers().map(users -> {
			model.addAttribute("users", users);
			return "index";
		});
	}

	@PostMapping("/saveUser")
	public Mono<String> saveUser(@ModelAttribute UserDTO user) {
		LOGGER.info("entered /saveUser with {}", user);
		return this.userService.saveUser(user).map(savedUser -> {
			LOGGER.info("saved new user {}", savedUser);
			return "redirect:/index";
		});
	}

	@PostMapping("/deactivateUser")
	public Mono<String> deactivateUser(@RequestParam String link) {
		LOGGER.info("entered /deactivateUser with {}", link);
		Objects.requireNonNull("User self rel link for deactivation not found", link);
		return this.userService.deactivateUser(link).map(deactivatedUser -> {
			LOGGER.info("deactivated user {}", deactivatedUser);
			return "redirect:/index";
		});
	}

}
