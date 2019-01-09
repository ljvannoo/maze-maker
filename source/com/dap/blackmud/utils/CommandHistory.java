package com.dap.blackmud.utils;

import java.util.Vector;

public class CommandHistory {
	private Vector history = null;
	private int currentCommand = Constants.UNDEFINED_INT;
	
	public CommandHistory() {
		history = new Vector();
	}
	
	public void appendCommand(String command) {
		history.add(command);
		resetCurrentCommand();
	}
	
	public String getLastCommand() {
		if(history.size() == 0)
			return "";
		return (String)history.get(history.size()-1);
	}

	public String getPreviousCommand() {
		String command = getCurrentCommand();
		currentCommand--;
		if(currentCommand < 0)
			currentCommand = 0;
		return command;
	}

	public String getNextCommand() {
		currentCommand++;
		if(currentCommand >= history.size()) {
			currentCommand = history.size()-1;
			return "";
		}
		return getCurrentCommand();
	}
	
	public String getCurrentCommand() {
		if(currentCommand == Constants.UNDEFINED_INT || currentCommand < 0 || currentCommand >= history.size())
			return "";
		return (String)history.get(currentCommand);
	}
	
	public void resetCurrentCommand() {
		currentCommand = history.size()-1;
	}
}
