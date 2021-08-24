package com.example.springuithymeleaf.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		model.addAttribute("user", new UserDTO());
		return this.userService.getUsers().map(users -> {
			model.addAttribute("users", users);
			return "index";
		});
	}

	@PostMapping("/saveUser")
	public Mono<String> saveUser(@ModelAttribute UserDTO user) {
		LOGGER.info("entered /saveUser");
		return this.userService.saveUser(user).map(savedUser -> {
			LOGGER.info("saved new user {}", savedUser);
			return "redirect:/index";
		});
	}

	@RequestMapping("/deleteUser/{id}")
	public Mono<String> deactivateUser(@PathVariable(name = "id") long id) {
		LOGGER.info("entered /deactivateUser/{}", id);
		return this.userService.deactivateUserById(id).map(count -> {
			LOGGER.info("deactivated {} users", count);
			return "redirect:/index";
		});
	}

}
