package de.nieder.kick.controller;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GameTO {
	@NotNull
	private String id;
	@NotNull
	private String teamOneId;
	@NotNull
	private String teamTwoId;

	private String kickerId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeamOneId() {
		return teamOneId;
	}

	public void setTeamOneId(String teamOneId) {
		this.teamOneId = teamOneId;
	}

	public String getTeamTwoId() {
		return teamTwoId;
	}

	public void setTeamTwoId(String teamTwoId) {
		this.teamTwoId = teamTwoId;
	}

	public String getKickerId() {
		return kickerId;
	}

	public void setKickerId(String kickerId) {
		this.kickerId = kickerId;
	}

}
