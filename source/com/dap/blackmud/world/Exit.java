package com.dap.blackmud.world;

import java.io.Serializable;

import com.dap.blackmud.utils.BitVector;
import com.dap.blackmud.utils.Command;
import com.dap.blackmud.utils.Constants;


public class Exit implements Serializable {
	private static final long serialVersionUID = -3180359869094451582L;
	
	public static final String[] DIRECTION_NAMES = {
			"NORTH",
			"EAST",
			"SOUTH",
			"WEST",
			"UP",
			"DOWN"};
	public static final int DIRECTION_NORTH	= 0;
	public static final int DIRECTION_EAST 	= 1;
	public static final int DIRECTION_SOUTH = 2;
	public static final int DIRECTION_WEST 	= 3;
	public static final int DIRECTION_UP 	= 4;
	public static final int DIRECTION_DOWN 	= 5;
	
	public static final int DEFAULT_DIRECTION	= DIRECTION_NORTH;
	public static final int MINIMUM_DIRECTION	= DIRECTION_NORTH;
	public static final int MAXIMUM_DIRECTION	= DIRECTION_DOWN;
	public static final int INVALID_DIRECTION	= Constants.UNDEFINED_INT;
	
	public static final String[] DOOR_STATES = {
		"open",
		"closed",
		"locked"
	};
	public static int DEFAULT_STATE = 0;

	public static final String[][] EXIT_FLAGS ={
		{"Door", 			"1", 		"true"},
		{"Climb", 			"2", 		"true"}};
	
	public static final String[][] DOOR_FLAGS ={
		{"Pickproof", 		"1", 		"true"},
		{"Secret", 			"2", 		"true"},
		{"No magic", 		"4", 		"true"}};
	
	public static final String[] DIFFICULTY_LEVELS = {
		"effortless",
		"easy",
		"simple",
		"tough",
		"resistant",
		"hard",
		"arduous",
		"complex",
		"intricate",
		"hopeless"
	};

	private int dir = DEFAULT_DIRECTION;
	private String keywords = null;
	private BitVector doorFlags = null;
	private boolean door = false;
	private boolean climb = false;
	private boolean external = false;
	private int difficultyLevel = 0;
	private int key = Constants.UNDEFINED_INT;
	private int state = DEFAULT_STATE;
	
	public Exit() {
		dir = DEFAULT_DIRECTION;
		keywords = "";
		doorFlags = new BitVector();
		door = false;
		climb = false;
		difficultyLevel = 0; 
		key = Constants.UNDEFINED_INT;
		state = DEFAULT_STATE;
	}
	
	public int setDirection(int direction) {
		if(!isValidDirection(direction)) {
			return INVALID_DIRECTION;
		}
		dir = direction;
		return direction;
	}

	public int getDirection() {
		return dir;
	}
	
	public static boolean isValidDirection(int direction) {
		if(direction < MINIMUM_DIRECTION || direction > MAXIMUM_DIRECTION) {
			return false;
		}
		return true;
	}
	
	public static String convertDirection(int direction) {
		if(!isValidDirection(direction))
			return "INVALID_DIRECTION";
		return DIRECTION_NAMES[direction];
	}
	
	public static int convertDirection(String direction) {
		direction = direction.toUpperCase();
		for(int i = 0; i < DIRECTION_NAMES.length; i++) {
			if(Command.isAbbreviation(direction, DIRECTION_NAMES[i]))
				return i;
		}
		return INVALID_DIRECTION;
	}
	
	public static int getInverseDirection(int direction) {
		int inverseDirection = INVALID_DIRECTION;
		switch(direction) {
			case DIRECTION_NORTH:
				inverseDirection = DIRECTION_SOUTH;
				break;
			case DIRECTION_EAST:
				inverseDirection = DIRECTION_WEST;
				break;
			case DIRECTION_SOUTH:
				inverseDirection = DIRECTION_NORTH;
				break;
			case DIRECTION_WEST:
				inverseDirection = DIRECTION_EAST;
				break;
			case DIRECTION_UP:
				inverseDirection = DIRECTION_DOWN;
				break;
			case DIRECTION_DOWN:
				inverseDirection = DIRECTION_UP;
				break;
		}
		return inverseDirection;
	}

	public int getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public BitVector getDoorFlags() {
		if(doorFlags == null) {
			doorFlags = new BitVector();
		}
		return doorFlags;
	}

	public void setDoorFlags(BitVector doorFlags) {
		this.doorFlags = doorFlags;
	}

	public boolean isClimb() {
		return climb;
	}

	public void setClimb(boolean isClimb) {
		this.climb = isClimb;
	}

	public boolean isDoor() {
		return door;
	}

	public void setDoor(boolean isDoor) {
		this.door = isDoor;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public static int getDoorFlagValue(String flagName) {
		flagName = flagName.toLowerCase();
		String curFlagName = null;
		for(int i = 0; i < DOOR_FLAGS.length; i++) {
			curFlagName = DOOR_FLAGS[i][0].toLowerCase();
			if(curFlagName.equals(flagName)) {
				return Integer.parseInt(DOOR_FLAGS[i][1]);
			}
		}
		return Constants.UNDEFINED_INT;
	}
	
	public static int getDoorStateValue(String stateName) {
		stateName = stateName.toLowerCase();
		for(int i = 0; i < DOOR_STATES.length; i++) {
			if(stateName.equals(DOOR_STATES[i]))
				return i;
		}
		return Constants.UNDEFINED_INT;
	}

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

}
