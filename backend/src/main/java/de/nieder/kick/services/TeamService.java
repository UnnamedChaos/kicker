package de.nieder.kick.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.nieder.kick.common.ServiceException;
import de.nieder.kick.controller.TeamTO;
import de.nieder.kick.model.Team;
import de.nieder.kick.model.User;
import de.nieder.kick.repository.TeamRepository;
import de.nieder.kick.repository.UserRepository;

@Controller
public class TeamService extends GenericService<Team> {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private UserRepository userRepository;

	public List<Team> getTeams() {
		return teamRepository.findAll();
	}

	public Team create(Team team) {
		List<Team> teams = teamRepository.findAllByMembersIn(team.getMembers());
		if (teams.size() > 0) {
			throw new ServiceException("create", "User can only be in one team");
		}
		return save(team);

	}

	public Team save(Team team) {
		return teamRepository.save(team);
	}

	public void delete(String teamId) {
		Optional<Team> team = teamRepository.findById(teamId);
		if (team.isPresent()) {
			teamRepository.delete(team.get());
		}
	}

	public Team mapTO(TeamTO teamTO) {
		Team team = new Team();
		List<User> members = userRepository.findAllByIdIn(teamTO.getMembers());
		team.setMembers(members);
		return team;
	}

	public Team modify(Team team) {
		Optional<Team> optional = teamRepository.findById(team.getId());
		if (optional.isPresent()) {
			return save(team);
		} else {
			throw new ServiceException("modify", "Cannot find team");
		}
	}
}
