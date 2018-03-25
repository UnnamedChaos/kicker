package de.nieder.kick.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.nieder.kick.controller.UserTO;
import de.nieder.kick.model.User;
import de.nieder.kick.repository.UserRepository;

@Controller
public class UserService extends GenericService<User> {

	@Autowired
	private UserRepository repository;

	public List<User> getUsers() {
		return repository.findAll();
	}

	public User create(User user) {
		if (repository.findByName(user.getName()).isEmpty()) {
			return save(user);
		}
		throw new IllegalArgumentException("User already exists");
	}

	public User save(User user) {
		return repository.save(user);
	}

	public User mapTO(UserTO userTO) {
		User user = new User();
		user.setName(userTO.getName());
		return user;
	}
}
