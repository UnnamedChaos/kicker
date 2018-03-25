package de.nieder.kick.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.nieder.kick.model.Game;
import de.nieder.kick.services.GameService;

@RestController
@RequestMapping(path = "/api/v1/games")
public class GameController {

	@Autowired
	private GameService gameService;

	@GetMapping()
	public List<Game> gameGames() {
		return gameService.getGames();
	}

	@PostMapping
	public Game createGame(@Valid @RequestBody GameTO gameTO) {
		return gameService.create(gameService.mapTO(gameTO));
	}

	@PatchMapping
	public Game modifyTeam(@Valid @RequestBody GameTO game) {
		return gameService.modify(gameService.mapTO(game));
	}

	@DeleteMapping
	public ResponseEntity<?> deleteTeam(@PathParam(value = "gameId") String gameId) {
		gameService.delete(gameId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
