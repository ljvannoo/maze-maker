package com.dap.blackmud.utils;

public class Command {
	private String command = null;
	private String method = null;
	private String description = null;
	
	public Command(String command, String method, String description) {
		this.command = command;
		this.method = method;
		this.description = description;
	}
	
	public String getCommand() {
		return command;
	}

	public String getMethod() {
		return method;
	}
	
	public static boolean isAbbreviation(String abbreviation, String string) {
		String cmd = string.toLowerCase();
		String abbrev = abbreviation.toLowerCase();
		return cmd.startsWith(abbrev);
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Command) {
			Command otherCommand = (Command)obj;
			if(getCommand().equals(otherCommand.getCommand()))
				return true;
		} else if(obj instanceof String) {
			String otherCommand = (String)obj;
			if(isAbbreviation(otherCommand, getCommand()))
				return true;
		}
		
		return false;
	}

	public String getDescription() {
		return description;
	}
}
