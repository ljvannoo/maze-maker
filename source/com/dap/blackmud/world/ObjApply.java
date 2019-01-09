package com.dap.blackmud.world;

import java.io.Serializable;

public class ObjApply implements Serializable {
	private static final long serialVersionUID = -1749192331160954440L;

	public static final String[][] APPLY_LOCATIONS = {
			{"None", 					"0",  "false"},
			{"Strength", 				"1",  "true"},
			{"Dexterity", 				"2",  "true"},
			{"Intelligence", 			"3",  "true"},
			{"Wisdom", 					"4",  "true"},
			{"Constitution", 			"5",  "true"},
			{"Charisma", 				"6",  "true"},
			{"Sex", 					"7",  "true"},
			{"Level", 					"8",  "false"},
			{"Age", 					"9",  "true"},
			{"Character Weight", 		"10", "false"},
			{"Character Height", 		"11", "false"},
			{"Mana", 					"12", "true"},
			{"Hitpoints", 				"13", "true"},
			{"Move points", 			"14", "true"},
			{"Gold", 					"15", "false"},
			{"Experience", 				"16", "false"},
			{"Armor-Class", 			"17", "true"},
			{"Hitroll", 				"18", "true"},
			{"Damroll", 				"19", "true"},
			{"Save vs Paralysis", 		"20", "true"},
			{"Save vs Rod", 			"21", "true"},
			{"Save vs Petrification",	"22", "true"},
			{"Save vs Breath", 			"23", "true"},
			{"Save vs Spell", 			"24", "true"},
			{"Save vs All", 			"25", "true"},
			{"Immunity", 				"26", "true"},
			{"Susceptibility", 			"27", "true"},
			{"M Immunity", 				"28", "false"},
			{"Spell", 					"29", "true"},
			{"Weapon Spell", 			"30", "true"},
			{"Eat Spell", 				"31", "true"},
			{"Backstab", 				"32", "true"},
			{"Kick", 					"33", "true"},
			{"Sneal", 					"34", "true"},
			{"Hide", 					"35", "true"},
			{"Bash", 					"36", "true"},
			{"Pick", 					"37", "true"},
			{"Steal", 					"38", "true"},
			{"Track", 					"39", "true"},
			{"Hit-N-Dam", 				"40", "true"},
			{"Spellfail", 				"41", "true"},
			{"Attacks", 				"42", "true"},
			{"Haste", 					"43", "true"},
			{"Slow", 					"44", "true"},
			{"BV2", 					"45", "false"},
			{"Find Traps", 				"46", "true"},
			{"Ride", 					"47", "true"},
			{"Race Slayer", 			"48", "true"},
			{"Align Slayer", 			"49", "true"},
			{"Mana Regen", 				"50", "true"},
			{"Hitpoint Regen", 			"51", "true"},
			{"Movement Regen", 			"52", "true"},
			{"Movement Bonus", 			"53", "true"},
			{"Intrinsic", 				"54", "false"}
	};
	
	private int location = 0;
	private int modifier = 0;
	
	public ObjApply(int location, int modifier) {
		location = 0;
		modifier = 0;
	}
	
	public int getLocation() {
		return location;
	}
	
	public int getModifier() {
		return modifier;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}
}
