package de.nieder.kick.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Game extends BaseEntity {
	private KickerTable kickerTable;

	@NotNull
	@OneToOne
	private Team teamOne;
	@OneToOne
	private Team teamTwo;

	private boolean running;

	public KickerTable getKickerTable() {
		return kickerTable;
	}

	public void setKickerTable(KickerTable kickerTable) {
		this.kickerTable = kickerTable;
	}

	public Team getTeamOne() {
		return teamOne;
	}

	public void setTeamOne(Team teamOne) {
		this.teamOne = teamOne;
	}

	public Team getTeamTwo() {
		return teamTwo;
	}

	public void setTeamTwo(Team teamTwo) {
		this.teamTwo = teamTwo;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
