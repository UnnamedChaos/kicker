package de.nieder.kick.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.nieder.kick.model.Game;

public interface GameRepository extends CrudRepository<Game, String> {

	public List<Game> findAll();

}
