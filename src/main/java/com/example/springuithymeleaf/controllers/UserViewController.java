package com.example.springuithymeleaf.controllers;

import java.util.List;

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

@Controller
public class UserViewController {

	@Autowired
	private UserService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserViewController.class);

	@GetMapping({ "", "/index" })
	public String showUserList(Model model) {
		LOGGER.info("entered /index");
		model.addAttribute("user", new UserDTO());
		List<UserDTO> users = this.userService.getUsers();
		LOGGER.info("users = {}", users);
		model.addAttribute("users", users);
		return "index";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute UserDTO user) {
		LOGGER.info("entered /saveUsers");
		UserDTO savedUser = this.userService.saveUser(user);
		LOGGER.info("saved new user {}", savedUser);
		return "redirect:/index";
	}

	@RequestMapping("/deleteUser/{id}")
	public String deactivateUser(@PathVariable(name = "id") long id) {
		LOGGER.info("entered /deleteUser/{}", id);
		int count = this.userService.deactivateUserById(id);
		LOGGER.info("deactivated {} users", count);
		return "redirect:/index";
	}

}
