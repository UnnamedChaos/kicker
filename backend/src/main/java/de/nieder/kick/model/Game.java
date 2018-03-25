package de.nieder.kick.model;

import javax.persistence.Entity;

@Entity
public class Game extends BaseEntity {
	private KickerTable kickerTable;

	private Team teamOne;

	private Team teamTwo;

}
