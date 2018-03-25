package de.nieder.kick.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.nieder.kick.common.ServiceException;
import de.nieder.kick.controller.GameTO;
import de.nieder.kick.model.Game;
import de.nieder.kick.repository.GameRepository;
import de.nieder.kick.repository.UserRepository;

@Controller
public class GameService extends GenericService<Game> {

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TeamService teamService;

	public List<Game> getGames() {
		return gameRepository.findAll();
	}

	public Game create(Game game) {

		return save(game);

	}

	public void verify(Game game) {
		if (game.getTeamOne().equals(game.getTeamTwo())) {
			throw new ServiceException("create Team", "The same Team cannot play against each other");
		}
	}

	public Game save(Game game) {
		verify(game);
		return gameRepository.save(game);
	}

	public void delete(String gameId) {
		Optional<Game> game = gameRepository.findById(gameId);
		if (game.isPresent()) {
			gameRepository.delete(game.get());
		}
	}

	public Game mapTO(GameTO gameTO) {
		Game game = new Game();
		// todo kicker
		game.setId(gameTO.getId());
		game.setTeamOne(teamService.findTeam(gameTO.getTeamOneId()));
		game.setTeamTwo(teamService.findTeam(gameTO.getTeamTwoId()));
		game.setRunning(false);
		return game;
	}

	public Game modify(Game game) {
		Optional<Game> optional = gameRepository.findById(game.getId());
		if (optional.isPresent()) {
			Game saved = optional.get();
			// kicker table from game
			saved.setKickerTable(game.getKickerTable());
			saved.setTeamOne(game.getTeamOne());
			saved.setTeamTwo(game.getTeamTwo());
			saved.setRunning(game.isRunning());
			return save(saved);
		} else {
			throw new ServiceException("modify", "Cannot find game");
		}
	}
}
