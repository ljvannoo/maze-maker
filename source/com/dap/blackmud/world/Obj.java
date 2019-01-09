package com.dap.blackmud.world;

import com.dap.blackmud.utils.BitVector;

public class Obj extends Entity {
	private static final long serialVersionUID = 4036954614021339004L;
	
	public static final int UNDAMAGABLE = -1;
	public static final int MAX_DURABILITY = 100;
	
	public static final int UNRENTABLE = -1;
	public static final int MAX_EGO = 1000;
	

	public static final String[][] EXTRA_FLAGS ={
		{"Glow", 		"1", 		"true"},
		{"Hum", 		"2", 		"true"},
		{"Metal", 		"4", 		"true"},
		{"Anti-Druid", 	"8", 		"false"},
		{"Organic", 	"16", 		"true"},
		{"Invisible", 	"32", 		"true"},
		{"Magic", 		"64", 		"true"},
		{"No-Drop",		"128", 		"true"},
		{"Bless", 		"256", 		"true"},
		{"Anti-Good",	"512", 		"true"},
		{"Anti-Evil",	"1024", 	"true"},
		{"Anti-Neutral","2048", 	"true"},
		{"Anti-Cleric",	"4096", 	"false"},
		{"Anti-Mage",	"8192", 	"false"},
		{"Anti-Thief",	"16384", 	"false"},
		{"Anti-Fighter","32768", 	"false"},
		{"Brittle",		"65536", 	"true"},
		{"Resistant",	"131072", 	"false"},
		{"Anti-Monk",	"262144", 	"false"},
		{"Anti-Men",	"524288", 	"true"},
		{"Anti-Women",	"1048576", 	"true"},
		{"Anti-Sun",	"2097152", 	"true"},
		{"Inset",		"4194304", 	"false"},
		{"Figurine",	"8388608", 	"false"},
		{"Warped",		"16777216", "true"},
		{"Restricted",	"33554432", "true"},
		{"Two-Handed",	"67108864", "true"},
		{"Armed",		"134217728","false"},
		{"Artifact",	"268435456","true"},
		{"Protected",	"536870912","true"}};
	
	public static final String[][] WEAR_FLAGS ={
		{"Take", 		"1", 		"true"},
		{"Finger", 		"2", 		"true"},
		{"Neck", 		"4", 		"true"},
		{"Body", 		"8", 		"true"},
		{"Head", 		"16", 		"true"},
		{"Legs", 		"32", 		"true"},
		{"Feet", 		"64", 		"true"},
		{"Hands",		"128", 		"true"},
		{"Arms", 		"256", 		"true"},
		{"Shield",		"512", 		"true"},
		{"About Body",	"1024", 	"true"},
		{"Waist",		"2048", 	"true"},
		{"Wrist",		"4096", 	"true"},
		{"Wield",		"8192", 	"true"},
		{"Hold",		"16384", 	"true"},
		{"Face",		"32768", 	"true"},
		{"About Legs",	"65536", 	"true"},
		{"Hung",		"131072", 	"true"},
		{"Strapped",	"262144", 	"true"}};
	
	public static final String[][] EGO_LIST ={
		{"Thibor-sized", 	"1471","true"},
		{"creator", 		"901", "true"},
		{"godly", 			"801", "true"},
		{"egomaniac", 		"676", "true"},
		{"herculean", 		"651", "true"},
		{"monstrous", 		"626", "true"},
		{"colossal", 		"601", "true"},
		{"gargantuan", 		"576", "true"},
		{"mammoth", 		"551", "true"},
		{"humongous", 		"526", "true"},
		{"gigantic", 		"501", "true"},
		{"enormous", 		"476", "true"},
		{"huge", 			"451", "true"},
		{"impressive", 		"426", "true"},
		{"large", 			"401", "true"},
		{"strong", 			"376", "true"},
		{"notable", 		"351", "true"},
		{"snotty", 			"301", "true"},
		{"fair", 			"276", "true"},
		{"average", 		"250", "true"},
		{"moderate", 		"226", "true"},
		{"normal", 			"201", "true"},
		{"boring", 			"176", "true"},
		{"unimpressive", 	"151", "true"},
		{"mediocre", 		"126", "true"},
		{"small", 			"106", "true"},
		{"wussy", 			"86",  "true"},
		{"humble", 			"76",  "true"},
		{"wimpy", 			"61",  "true"},
		{"trifling", 		"46",  "true"},
		{"miniscule", 		"31",  "true"},
		{"teenie weenie", 	"16",  "true"},
		{"itty bitty", 		"0",   "true"},
		{"unrentable", 		"-1",   "true"}
	};
	
	public static final String[] WEAPON_TYPES = {
		"INVALID WEAPON TYPE",
		"Stab",
		"Whip",
		"Slash",
		"Smash",
		"Cleave",
		"Crush",
		"Pound",
		"Claw",
		"Bite",
		"Sting",
		"Pierce",
		"Blast",
		"Hit"
	};
	
	public static final String[] DRINK_TYPES = {
		"Water",
		"Beer",
		"Wine",
		"Ale",
		"Dark Ale",
		"Vodka",
		"Lemonade",
		"Grain",
		"Local Specialty",
		"Slime",
		"Milk",
		"Tea",
		"Coffee",
		"Blood",
		"Saltwater",
		"Coke"
	};
	
	private ObjType type;
	private BitVector extraFlags;
	private BitVector wearFlags;
	private int[] values = {0, 0, 0, 0};
	private int durability;
	private int weight;
	private int value;
	private int ego;
	private ExtraDescriptionList extraDescriptions;
	private ApplyList applies;
	public Obj(int VNUM) {
		super();
		addField("VNUM", new Integer(VNUM));
		addField("keywords", "object default");
		addField("shortDesc", "a default object");
		addField("longDesc", "A default object has been left here.");
		type = new ObjType();
		extraFlags = new BitVector();
		wearFlags = new BitVector();
		durability = MAX_DURABILITY;
		weight = 0;
		value = 0;
		ego = 0;
		extraDescriptions = new ExtraDescriptionList();
		applies = new ApplyList();
	}
}
