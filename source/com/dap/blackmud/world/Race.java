package com.dap.blackmud.world;

import java.io.Serializable;

public class Race implements Serializable{
	private static final long serialVersionUID = -6727508175091617734L;
	
	private String name;
	private boolean playerRace;
	
	public Race(String name, boolean playerRace) {
		this.name = name;
		this.playerRace = playerRace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPlayerRace() {
		return playerRace;
	}

	public void setPlayerRace(boolean playerRace) {
		this.playerRace = playerRace;
	}
	
	public String toString() {
		return name;
	}
}
