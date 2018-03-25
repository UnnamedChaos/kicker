package de.nieder.kick.controller;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserTO {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
