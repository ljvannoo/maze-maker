package com.dap.blackmud.world;

import java.io.Serializable;
import java.util.Hashtable;

public class ExitList implements Serializable {
	private static final long serialVersionUID = 6075484720924504263L;
	private Hashtable exits = null; 
	
	public ExitList() {
		exits = new Hashtable();
	}
	
	public Exit getExit(int direction) {
		if(!Exit.isValidDirection(direction))
			return null;
		return (Exit)exits.get(new Integer(direction));
	}
	
	public Exit getExit(String direction) {
		return getExit(Exit.convertDirection(direction));
	}
	
	public int getExitCount() {
		return exits.size();
	}
	
	public boolean hasExit(int direction) {
		if(!Exit.isValidDirection(direction))
			return false;
		return exits.containsKey(new Integer(direction));
	}

	public void addExit(int direction) {
		if(Exit.isValidDirection(direction)) {
			Exit newExit = new Exit();
			newExit.setDirection(direction);
			exits.put(new Integer(direction), newExit);
		}
	}

	public void removeExit(int direction) {
		if(Exit.isValidDirection(direction) && hasExit(direction)) {
			exits.remove(new Integer(direction));
		}
	}
}
