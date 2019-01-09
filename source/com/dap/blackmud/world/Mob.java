package com.dap.blackmud.world;

import com.dap.blackmud.utils.BitVector;


public class Mob extends Entity {
	private static final long serialVersionUID = -9038067774189345182L;
	public static final int MIN_LEVEL = 1;
	public static final int MAX_LEVEL = 50;
	public static final int DEFAULT_LEVEL = 1;
	
	public static final int MIN_ALIGNMENT = -1000;
	public static final int MAX_ALIGNMENT = 1000;
	public static final int DEFAULT_ALIGNMENT = 0;
	
	public static final int MIN_ATTACKS = 1;
	public static final int MAX_ATTACKS = 10;
	public static final int DEFAULT_ATTACKS = 1;
	
	public static final int GENDER_NEUTER = 0;
	public static final int GENDER_MALE = 1;
	public static final int GENDER_FEMALE = 2;
	
	public static final MobPosition[] POSITIONS = {
		new MobPosition("DEAD", 		false),
		new MobPosition("M. WOUNDED", false),
		new MobPosition("INCAPACITATED", false),
		new MobPosition("STUNNED", 		false),
		new MobPosition("SLEEPING", 	true),
		new MobPosition("RESTING", 		true),
		new MobPosition("SITTING", 		true),
		new MobPosition("FIGHTING", 	false),
		new MobPosition("STANDING", 	true),
		new MobPosition("MOUNTED", 		false)
	};
	
	public static final Race[] RACES = {
		new Race("HALFBREED", 	true),
		new Race("HUMAN", 		true),
		new Race("ELVEN", 		true),
		new Race("DWARF", 		true),
		new Race("HALFLING", 	true),
		new Race("GNOME", 		true),
		new Race("DROW", 		true),
		new Race("HALFELF", 	true),
		new Race("HALFORC", 	true),
		new Race("OGRE", 		true),
		new Race("FAERIE", 		true),
		new Race("ORC", 		false),
		new Race("INSECT", 		false),
		new Race("ARACHNID", 	false),
		new Race("DINOSAUR", 	false),
		new Race("FISH", 		false),
		new Race("AVIAN", 		false),
		new Race("GIANT", 		false),
		new Race("CRUSTACEAN", 	false),
		new Race("PARASITE", 	false),
		new Race("SLIME", 		false),
		new Race("DEMON", 		false),
		new Race("WORM", 		false),
		new Race("BACTERIA", 	false),
		new Race("TREE", 		false),
		new Race("VEGGIE", 		false),
		new Race("ELEMENTAL", 	false),
		new Race("HALF_GIANT", 	false),
		new Race("AMPHIBIAN", 	false),
		new Race("GHOST", 		false),
		new Race("GOBLINOID", 	false),
		new Race("TROLL", 		false),
		new Race("VEGMAN", 		false),
		new Race("JELLY", 		false),
		new Race("BEASTMAN", 	false),
		new Race("LICH", 		false),
		new Race("REPTILE", 	false),
		new Race("GOLEM", 		false),
		new Race("MIRLAD", 		false),
		new Race("TROGMAN", 	false),
		new Race("WEP", 		false),
		new Race("GRALNER", 	false),
		new Race("MAMMAL", 		false),
		new Race("WHALE", 		false),
		new Race("BEAR", 		false),
		new Race("ELFKIN", 		false),
		new Race("HORSE", 		false),
		new Race("FUNGI", 		false),
		new Race("ASTRAL", 		false),
		new Race("TREANT", 		false),
		new Race("DRAGON", 		false),
		new Race("LYCANTH", 	false),
		new Race("MOLLUSK", 	false),
		new Race("WAMPHYRI", 	false),
		new Race("UNDEAD", 		false),
		new Race("SPECIAL", 	false)
	};
	
//	 Room Constants
	// Format: {<label>, <bit>, <available>} 
	public static final String[][] AFFECTION_FLAGS ={
		{"Blind", 			"1", 		"true"},
		{"Invisible", 		"2", 		"true"},
		{"Detect Evil", 	"4", 		"true"},
		{"Detect Invis.", 	"8", 		"true"},
		{"Detect Magic", 	"16", 		"true"},
		{"Sense Life", 		"32", 		"true"},
		{"Life Protect", 	"64", 		"false"},
		{"Sanctuary", 		"128", 		"true"},
		{"Dragon Ride",		"256", 		"false"},
		{"Growth", 			"512", 		"false"},
		{"Cursed",			"1024", 	"true"},
		{"Levitating",		"2048", 	"true"},
		{"Posioned",		"4096", 	"false"},
		{"Tree Travel",		"8192", 	"false"},
		{"Paralysis",		"16384", 	"false"},
		{"Infravision",		"32768", 	"true"},
		{"Waterbreath",		"65536", 	"true"},
		{"Sleep",			"131072", 	"false"},
		{"Travelling",		"262144", 	"false"},
		{"Sneak",			"524288", 	"true"},
		{"Hide",			"1048576", 	"true"},
		{"Silence",			"2097152", 	"true"},
		{"Charm",			"4194304", 	"true"},
		{"Follow",			"8388608", 	"false"},
		{"Dirt Dweller",	"16777216", "true"},
		{"True Sight",		"33554432", "true"},
		{"Scrying",			"67108864", "false"},
		{"Fireshield",		"134217728","true"}};
	
	
	private BitVector actionFlags;
	private BitVector affectionFlags;
	private BitVector resists;
	private BitVector immunities;
	private BitVector susceptibilities;
	private boolean bashable;
	private Race race;
	
	public Mob(int VNUM) {
		super();
		addField("VNUM", new Integer(VNUM));
		addField("keywords", "puff dragon fractal");
		addField("shortDesc", "Puff");
		addField("longDesc", "Puff the Fractal Dragon is here, contemplating a higher reality.");
		addField("fullDesc", "You see nothing special about her.");
		actionFlags = new BitVector();
		affectionFlags = new BitVector();
		addField("alignment", new Integer(0));
		addField("attacks", new Integer(1));
		addField("level", new Integer(1));
		addField("hitroll", new Integer(24));
		addField("damroll", new Integer(3));
		addField("ac", new Integer(-10));
		addField("hitpoints", new Integer(588));
		addField("minDamage", new Integer(6));
		addField("maxDamage", new Integer(24));
		addField("gold", new Integer(10000));
		addField("exp", new Integer(155000));
		addField("position", new Integer(8));
		addField("gender", new Integer(2));
		resists = new BitVector();
		immunities = new BitVector();
		susceptibilities = new BitVector();
		bashable = true;
		
		race = RACES[50];
	}

	public BitVector getActionFlags() {
		return actionFlags;
	}

	public void setActionFlags(BitVector actionFlags) {
		this.actionFlags = actionFlags;
	}

	public BitVector getAffectionFlags() {
		return affectionFlags;
	}

	public void setAffectionFlags(BitVector affectionFlags) {
		this.affectionFlags = affectionFlags;
	}

	public boolean isBashable() {
		return bashable;
	}

	public void setBashable(boolean bashable) {
		this.bashable = bashable;
	}

	public BitVector getImmunities() {
		return immunities;
	}

	public void setImmunities(BitVector immunities) {
		this.immunities = immunities;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public BitVector getResists() {
		return resists;
	}

	public void setResists(BitVector resists) {
		this.resists = resists;
	}

	public BitVector getSusceptibilities() {
		return susceptibilities;
	}

	public void setSusceptibilities(BitVector susceptibilities) {
		this.susceptibilities = susceptibilities;
	}

	public static String alignmentDescription(int value) {
		if (value <= -900)
			return("Deeds are legendary, even in hell!");
		else if (value <= -500)
			return("Rape and murder are a way of life!");
		else if (value <= -351)
			return("Truly evil!");
		else if (value <= -100)
			return("Tending toward evil.");
		else if (value <= 100)
			return("Balanced.");
		else if (value <= 350)
			return("Tending toward goodness.");
		else if (value <= 500)
			return("Sweet, caring, all that crapola!");
		else if (value <= 900)
			return("A real goody-goody!");

		return "The epitome of righteousness!";
	}
}