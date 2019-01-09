package com.dap.blackmud.world;

import java.io.Serializable;

public class ObjType implements Serializable {
	private static final long serialVersionUID = 8804135126443258609L;
	
	public static final String[][] TYPES = {
		{"Invalid Type",	"0",  "false"},
		{"Light", 			"1",  "true"},
		{"Scroll", 			"2",  "true"},
		{"Wand", 			"3",  "true"},
		{"Staff", 			"4",  "true"},
		{"Weapon", 			"5",  "true"},
		{"Ranged Weapon",	"6",  "true"},
		{"Missile", 		"7",  "true"},
		{"Treasure", 		"8",  "true"},
		{"Armor", 			"9",  "true"},
		{"Potion", 			"10", "true"},
		{"Worn", 			"11", "true"},
		{"Other", 			"12", "true"},
		{"Trash", 			"13", "true"},
		{"Trap", 			"14", "false"},
		{"Container", 		"15", "true"},
		{"Note", 			"16", "true"},
		{"Drink Container",	"17", "true"},
		{"Key", 			"18", "true"},
		{"Food", 			"19", "true"},
		{"Money", 			"20", "true"},
		{"Pen", 			"21", "true"},
		{"Boat", 			"22", "true"},
		{"Audio", 			"23", "false"},
		{"Board", 			"24", "true"},
		{"Tree", 			"25", "true"},
		{"Rock", 			"26", "true"},
		{"Head", 			"27", "false"},
		{"Ticket", 			"28", "false"},
		{"Tack", 			"29", "true"},
		{"Poison", 			"30", "false"},
		{"Skin", 			"31", "true"},
		{"Die", 			"32", "true"},
		{"Godstone", 		"33", "true"}};
	
	public static final int 	INVALID_TYPE	= 0;
	public static final int 	DEFAULT_TYPE 	= 1;
		
	private int currentType = DEFAULT_TYPE;
	
	public ObjType() {
		currentType = DEFAULT_TYPE;
	}
	
	public ObjType(int type) {
		currentType = type;
	}
	
	public ObjType(String type) {
		currentType = convert(type);
	}
	
	public int getNumber() {
		return currentType;
	}
	
	public String getName() {
		return convert(getNumber());
	}
	
	public boolean isValid() {
		return isValid(getNumber());
	}
	
	public static boolean isValid(int typeNumber) {
		return TYPES[typeNumber][2].equals("true");
	}
	
	public static int convert(String typeName) {
		for(int i = 0; i < TYPES.length; i++) {
			if(TYPES[i][0].equalsIgnoreCase(typeName))
				return i;
		}
		return INVALID_TYPE;
	}
	
	public static String convert(int typeNumber) {
		if(typeNumber < 0 || typeNumber >= TYPES.length)
			return "Invalid Type";
		return TYPES[typeNumber][0];
	}
}
