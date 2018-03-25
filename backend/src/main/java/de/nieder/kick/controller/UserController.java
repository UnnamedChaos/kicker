package de.nieder.kick.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.nieder.kick.model.User;
import de.nieder.kick.services.UserService;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping()
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@PostMapping
	public User createUser(@Valid @RequestBody UserTO userTO) {
		return userService.create(userService.mapTO(userTO));
	}

}
