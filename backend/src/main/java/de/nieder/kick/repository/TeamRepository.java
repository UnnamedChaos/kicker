package de.nieder.kick.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.nieder.kick.model.Team;
import de.nieder.kick.model.User;

public interface TeamRepository extends CrudRepository<Team, String> {

	public List<Team> findAll();

	public List<Team> findAllByMembersIn(List<User> members);
}
