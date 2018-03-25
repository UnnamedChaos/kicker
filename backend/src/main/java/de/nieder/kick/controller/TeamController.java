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

import de.nieder.kick.model.Team;
import de.nieder.kick.services.TeamService;

@RestController
@RequestMapping(path = "/api/v1/team")
public class TeamController {

	@Autowired
	private TeamService teamService;

	@GetMapping()
	public List<Team> getTeams() {
		return teamService.getTeams();
	}

	@PostMapping
	public Team createTeam(@Valid @RequestBody TeamTO teamTO) {
		return teamService.create(teamService.mapTO(teamTO));
	}

	@PatchMapping
	public Team modifyTeam(@Valid @RequestBody Team team) {
		return teamService.modify(team);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteTeam(@PathParam(value = "teamId") String teamId) {
		teamService.delete(teamId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
