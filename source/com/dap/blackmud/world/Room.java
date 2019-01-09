package com.dap.blackmud.world;

import com.dap.blackmud.utils.BitVector;
import com.dap.blackmud.utils.Constants;

public class Room extends Entity {
	private static final long serialVersionUID = 3360565949551297751L;
	
	// Room Constants
	// Format: {<label>, <bit>, <available>} 
	public static final String[][] ROOM_FLAGS ={
		{"Dark", 			"1", 		"true"},
		{"Death Trap", 		"2", 		"true"},
		{"No Mob", 			"4", 		"true"},
		{"Indoors", 		"8", 		"true"},
		{"Peaceful", 		"16", 		"true"},
		{"No Steal", 		"32", 		"true"},
		{"No Summon", 		"64", 		"true"},
		{"No Magic", 		"128", 		"true"},
		{"Tunnel",			"256", 		"true"},
		{"Private", 		"512", 		"true"},
		{"Silence",			"1024", 	"true"},
		{"Large",			"2048", 	"false"},
		{"No Death",		"4096", 	"false"},
		{"Save Room",		"8192", 	"false"},
		{"Rent - Cheap",	"16384", 	"true"},
		{"Rent - Average",	"32768", 	"true"},
		{"Rent - Nice",		"65536", 	"true"},
		{"Rent - Swank",	"131072", 	"true"},
		{"Rent - Free",		"262144", 	"true"},
		{"Good Temple",		"524288", 	"true"},
		{"Evil Temple",		"1048576", 	"true"},
		{"Neutral Temple",	"2097152", 	"true"},
		{"Good/Neut Temple","4194304", 	"true"},
		{"Evil/Neut Temple","8388608", 	"true"},
		{"OOG",				"16777216", "false"},
		{"Warehouse",		"33554432", "true"},
		{"Auction House",	"67108864", "true"}};
	
	public static final String[] SECTOR_TYPES = {
		"Inside",
		"City",
		"Field",
		"Forest",
		"Hills",
		"Mountains",
		"Water - Swim",
		"Water - No Swim",
		"Air",
		"Underwater",
		"Desert",
		"Tree",
		"Fire",
		"In-ground"};
	
	private ExitList exits = null;
	private ExtraDescriptionList extraDescriptions = null;
	private Coordinate coordinates = null;
	private BitVector flags = null;

	public Room(int VNUM, Coordinate newCoordinates) {
		super();
		addField("VNUM", new Integer(VNUM));
		coordinates = newCoordinates;
		addField("title", "Empty Room");
		addField("description", "There is nothing in this room.");
		flags = new BitVector();
		addField("sectorType", new Integer(0));
		addField("mobLimit", new Integer(0));
		exits = new ExitList();
		extraDescriptions = new ExtraDescriptionList();
	}
	
	public Room(Room baseRoom) {
		super();
		addField("VNUM", baseRoom.getValue("VNUM"));
		coordinates = new Coordinate(baseRoom.getCoordinates());
		addField("title", baseRoom.getValue("title"));
		addField("description", baseRoom.getValue("description"));
		flags = new BitVector(baseRoom.getFlags().toInt());
		addField("sectorType", baseRoom.getValue("sectorType"));
		addField("mobLimit", baseRoom.getValue("mobLimit"));
		exits = new ExitList();
		extraDescriptions = new ExtraDescriptionList(baseRoom.getExtraDescriptions());
	}
	
	public int getExitCount() {
		return exits.getExitCount();
	}

	public ExitList getExits() {
		return exits;
	}
	
	public Coordinate getCoordinates() {
		return coordinates;
	}

	public void addExit(int direction) {
		exits.addExit(direction);
	}
	
	public void removeExit(int direction) {
		exits.removeExit(direction);
	}
	
	public boolean hasExit(int direction) {
		return exits.hasExit(direction);
	}
	
	public static int getFlagValue(String flagName) {
		flagName = flagName.toLowerCase();
		String curFlagName = null;
		for(int i = 0; i < ROOM_FLAGS.length; i++) {
			curFlagName = ROOM_FLAGS[i][0].toLowerCase();
			if(curFlagName.equals(flagName)) {
				return Integer.parseInt(ROOM_FLAGS[i][1]);
			}
		}
		return Constants.UNDEFINED_INT;
	}

	public int getVNUM() {
		return ((Integer)getValue("VNUM")).intValue();
	}
	
	public int getSectorType() {
		return ((Integer)getValue("sectorType")).intValue();
	}
	
	public BitVector getFlags() {
		return flags;
	}
	
	public void setFlags(int newFlags) {
		flags = new BitVector(newFlags);
	}

	public ExtraDescriptionList getExtraDescriptions() {
		return extraDescriptions;
	}

	public void setExtraDescriptions(ExtraDescriptionList extraDescriptions) {
		this.extraDescriptions = extraDescriptions;		
	}

	public Exit getExit(int dir) {
		if(Exit.isValidDirection(dir)) {
			return getExits().getExit(dir);
		}
		return null;
	}

	public boolean exitBlocked(int dir) {
		if(Exit.isValidDirection(dir) && hasExit(dir)) {
			Exit exit = getExits().getExit(dir);
			if(!exit.isDoor())
				return false;
			else if(exit.getState() != Exit.getDoorStateValue("open"))
				return true;
			else
				return false;
				
		}
		return true;
	}
	
	public String getTitle() {
		return (String)getValue("title");
	}
	
	public String toString() {
		return getCoordinates().toString();
	}
}
