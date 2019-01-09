package com.dap.blackmud.utils;

import java.util.Vector;

public class CommandList {
	private Vector commands = new Vector();
	
	public CommandList() {
		intialize();
	}
	
	private void intialize() {
		Command command = null;
		
		command = new Command("north", "doMoveNorth", "Moves one room to the north.");
		commands.add(command);
		
		command = new Command("east", "doMoveEast", "Moves one room to the east.");
		commands.add(command);
		
		command = new Command("south", "doMoveSouth", "Moves one room to the south.");
		commands.add(command);
		
		command = new Command("west", "doMoveWest", "Moves one room to the west.");
		commands.add(command);
		
		command = new Command("up", "doMoveUp", "Moves one room up.");
		commands.add(command);
		
		command = new Command("down", "doMoveDown", "Moves one room down.");
		commands.add(command);
		
		command = new Command("bump", "doBump", "Creates an external exit.");
		commands.add(command);
		
		command = new Command("edit", "doEdit", "Edits an entity.");
		commands.add(command);
		
		command = new Command("version", "doVersion", "Displays the application version information.");
		commands.add(command);
		
		command = new Command("new", "doNew", "Creates a new entity.");
		commands.add(command);
		
		command = new Command("open", "doOpen", "Opens a new file.");
		commands.add(command);
		
		command = new Command("save", "doSave", "Saves information to a file.");
		commands.add(command);
		
		command = new Command("print", "doPrint", "Prints information about an entity.");
		commands.add(command);
		
		command = new Command("help", "doHelp", "Displays helpful information.");
		commands.add(command);
		
		command = new Command("look", "doLook", "Displays the current room");
		commands.add(command);
		
		command = new Command("scan", "doScan", "Displays information about the surrounding rooms.");
		commands.add(command);
		
		command = new Command("echo", "doEcho", "Echos a line of text.");
		commands.add(command);
		
		command = new Command("create", "doCreate", "Creates a new entity.");
		commands.add(command);
	}

	public String findCommandMethod(String cmd) {
		Command currentCommand = findCommand(cmd);
		if(currentCommand != null)
			return currentCommand.getMethod();
		return null;
	}
	
	public Command findCommand(String cmd) {
		for(int i = 0; i < commands.size(); i++) {
			Command currentCommand = (Command)commands.get(i);
			if(currentCommand.equals(cmd))
				return currentCommand;
		}
		return null;
	}
	
	public Vector getCommandList() {
		Vector list = new Vector(commands.size());
		
		for(int i = 0; i < commands.size(); i++) {
			list.add(((Command)commands.get(i)).getCommand());
		}
		
		return list;
	}
}
