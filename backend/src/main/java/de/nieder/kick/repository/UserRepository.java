package de.nieder.kick.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.nieder.kick.model.User;

public interface UserRepository extends CrudRepository<User, String> {

	public List<User> findAll();

	public List<User> findByName(String name);

	public List<User> findAllByIdIn(List<String> ids);
}
