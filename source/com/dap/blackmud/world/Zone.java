package com.dap.blackmud.world;

import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.dap.blackmud.utils.BitVector;
import com.dap.blackmud.utils.FileWrapper;
import com.dap.blackmud.utils.Sorter;


public class Zone extends Entity {
	private static final long serialVersionUID = -4591507179881844868L;
	
	// Zone Constants
	// Format: {<label>, <bit>, <available>} 
	public static final String[][] ZONE_OPTIONS ={
		{"When Empty", 			"1", 	"true"},
		{"Always", 				"2", 	"true"},
		{"Astral", 				"4", 	"false"},
		{"Desert", 				"8", 	"true"},
		{"Artic", 				"16", 	"true"},
		{"Hades", 				"32", 	"false"},
		{"Olympus", 			"64", 	"false"},
		{"Abyss", 				"128", 	"false"},
		{"Prime Material Plane","256", 	"true"},
		{"Limbo", 				"512", 	"false"}};
	public static final int 	DEFAULT_ZONE_NUMBER 	= 1;
	public static final int 	DEFAULT_ZONE_VNUM 		= 100;
	public static final int 	DEFAULT_ZONE_LIFESPAN 	= 30;
	
	private Coordinate currentLocation = null;
	private Hashtable rooms = null;
	private BitVector resetBits = null;
	private RoomTemplateList roomTemplates = null;
	/**
	 * Creates a new zone
	 * @param number The zone number
	 * @param name The name of the zone
	 * @param startVNUM The starting VNUM for the zone
	 * @param lifespan The number of minutes between zone resets
	 * @param resetBits The behavior of the zone when it resets
	 */
	public Zone(int number, String name, int startVNUM, int lifespan, int resetBits) {
		super();
		addField("number", new Integer(number));
		addField("name", name);
		addField("startVNUM", new Integer(startVNUM));
		addField("nextVNUM", new Integer(startVNUM));
		addField("lifespan", new Integer(lifespan));
		this.resetBits = new BitVector(resetBits);
		rooms = new Hashtable();
		Coordinate origin = new Coordinate(0, 0, 0);
		insertDefaultRoom(origin);
		setCurrentLocation(origin);
		roomTemplates = new RoomTemplateList();
	}
	
	public String getName() {
		return ""+getValue("name");
	}
	
	public void insertDefaultRoom(Coordinate newCoordinates) {
		Room newRoom = new Room(getNextVNUM(true), newCoordinates);
		rooms.put(newCoordinates, newRoom);
	}

	public int getNextVNUM(boolean increment) {
		Integer nextVNUM = (Integer)getValue("nextVNUM");
		if(increment)
			setValue("nextVNUM", new Integer(nextVNUM.intValue()+1));
		return nextVNUM.intValue();
	}

	public Coordinate getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Coordinate currentLocation) {
		this.currentLocation = currentLocation;
	}

	public Room getCurrentRoom() {
		return getRoom(getCurrentLocation());
	}
	
	public Room getRoom(Coordinate location) {
		return (Room)rooms.get(location);
	}
	
	public boolean roomExistsAt(Coordinate location) {
		if(location != null)
			return rooms.containsKey(location);
		return false;
	}
	
	public void insertRoom(Room newRoom) {
		rooms.put(newRoom.getCoordinates(), newRoom);
		if(getCurrentLocation() == null)
			setCurrentLocation(newRoom.getCoordinates());
	}
	
	public void removeRoom(Coordinate location) {
		Room room = getRoom(location);
		if(room != null) {
			for(int i = Exit.MINIMUM_DIRECTION; i <= Exit.MAXIMUM_DIRECTION; i++) {
				disconnect(room, i);
			}
			if(location.equals(getCurrentLocation())) {
				Enumeration keys = rooms.keys();
				setCurrentLocation((Coordinate)keys.nextElement());
			}
			rooms.remove(location);
		}
	}

	public void connect(Room room, int direction) {
		Coordinate otherLocation = getNeighborLocation(room.getCoordinates(), direction);
		Room otherRoom = getRoom(otherLocation);
		if(otherRoom != null) {
			room.addExit(direction);
			otherRoom.addExit(Exit.getInverseDirection(direction));
		}
	}
	
	public void disconnect(Room room, int direction) {
		Coordinate otherLocation = getNeighborLocation(room.getCoordinates(), direction);
		Room otherRoom = getRoom(otherLocation);
		if(otherRoom != null) {
			room.removeExit(direction);
			otherRoom.removeExit(Exit.getInverseDirection(direction));
		}
	}
	
	public static Coordinate getNeighborLocation(Coordinate location, int direction) {
		Coordinate neighbor = null;
		switch(direction) {
			case Exit.DIRECTION_NORTH:
				neighbor = new Coordinate(location.getX(), location.getY()+1, location.getZ());
				break;
			case Exit.DIRECTION_EAST:
				neighbor = new Coordinate(location.getX()+1, location.getY(), location.getZ());
				break;
			case Exit.DIRECTION_SOUTH:
				neighbor = new Coordinate(location.getX(), location.getY()-1, location.getZ());
				break;
			case Exit.DIRECTION_WEST:
				neighbor = new Coordinate(location.getX()-1, location.getY(), location.getZ());
				break;
			case Exit.DIRECTION_UP:
				neighbor = new Coordinate(location.getX(), location.getY(), location.getZ()+1);
				break;
			case Exit.DIRECTION_DOWN:
				neighbor = new Coordinate(location.getX(), location.getY(), location.getZ()-1);
				break;
		}
		return neighbor;
	}

	public BitVector getResetBits() {
		return resetBits;
	}

	public RoomTemplateList getRoomTemplates() {
		return roomTemplates;
	}
	
	public void setRoomTemplates(RoomTemplateList newRoomTemplates) {
		roomTemplates = newRoomTemplates;
	}

	public void copyToRoom(Room room, RoomTemplate template) {
		Room templateRoom = template.getRoom();
		room.setValue("title", templateRoom.getValue("title"));
		room.setValue("description", templateRoom.getValue("description"));
		room.setFlags(templateRoom.getFlags().toInt());
		room.setValue("sectorType", templateRoom.getValue("sectorType"));
		room.setValue("mobLimit", templateRoom.getValue("mobLimit"));
		room.setExtraDescriptions(new ExtraDescriptionList(templateRoom.getExtraDescriptions()));
	}
	
	public void copyToCurrentRoom(RoomTemplate template) {
		copyToRoom(getCurrentRoom(), template);
	}
	

	public void setUpdateReciprocalExit(Room room, Exit exit) {
		Room otherRoom = getRoom(getNeighborLocation(room.getCoordinates(), exit.getDirection()));
		if(otherRoom != null) {
			Exit otherExit = otherRoom.getExit(Exit.getInverseDirection(exit.getDirection()));
			if(otherExit != null) {
				// If the other exit exists, this exit can't be external
				exit.setExternal(false);
				
				if(exit.isDoor()) {
					otherExit.setDoor(true);
					otherExit.setKeywords(exit.getKeywords());
					otherExit.setState(exit.getState());
					otherExit.setDoorFlags(new BitVector(exit.getDoorFlags().toInt()));
					if(exit.getDoorFlags().isFullSet(Exit.getDoorFlagValue("secret")))
						otherExit.setDifficultyLevel(exit.getDifficultyLevel());
					else
						otherExit.setDifficultyLevel(0);
				} else {
					otherExit.setDoor(false);
					otherExit.setKeywords("");
					otherExit.setState(Exit.DEFAULT_STATE);
					otherExit.setDoorFlags(new BitVector(0));
					otherExit.setDifficultyLevel(0);
				}
				otherExit.setClimb(exit.isClimb());
				otherExit.setExternal(exit.isExternal());
			} else {
				// If the other exit does NOT exist, this exit must be external
				exit.setExternal(true);
			}
		}
	}

	public int getMaxY() {
		Enumeration keys = rooms.keys();
		int maxY = 0;
		int curY = 0;
		if(keys.hasMoreElements()) {
			maxY = ((Coordinate)keys.nextElement()).getY();
			while(keys.hasMoreElements()) {
				curY = ((Coordinate)keys.nextElement()).getY();
				if(curY > maxY)
					maxY = curY;
			}
		}
//		System.out.println("Max Y = "+maxY);
		return maxY;
	}
	
	public int getMaxX() {
		Enumeration keys = rooms.keys();
		int maxX = 0;
		int curX = 0;
		if(keys.hasMoreElements()) {
			maxX = ((Coordinate)keys.nextElement()).getX();
			while(keys.hasMoreElements()) {
				curX = ((Coordinate)keys.nextElement()).getX();
				if(curX > maxX)
					maxX = curX;
			}
		}
//		System.out.println("Max X = "+maxX);
		return maxX;
	}
	
	public int getMaxZ() {
		Enumeration keys = rooms.keys();
		int maxZ = 0;
		int curZ = 0;
		if(keys.hasMoreElements()) {
			maxZ = ((Coordinate)keys.nextElement()).getZ();
			while(keys.hasMoreElements()) {
				curZ = ((Coordinate)keys.nextElement()).getZ();
				if(curZ > maxZ)
					maxZ = curZ;
			}
		}
//		System.out.println("Max X = "+maxX);
		return maxZ;
	}
	
	public int getMinY() {
		Enumeration keys = rooms.keys();
		int minY = 0;
		int curY = 0;
		if(keys.hasMoreElements()) {
			minY = ((Coordinate)keys.nextElement()).getY();
			while(keys.hasMoreElements()) {
				curY = ((Coordinate)keys.nextElement()).getY();
				if(curY < minY)
					minY = curY;
			}
		}
//		System.out.println("Min Y = "+minY);
		return minY;
	}
	
	public int getMinX() {
		Enumeration keys = rooms.keys();
		int minX = 0;
		int curX = 0;
		if(keys.hasMoreElements()) {
			minX = ((Coordinate)keys.nextElement()).getX();
			while(keys.hasMoreElements()) {
				curX = ((Coordinate)keys.nextElement()).getX();
				if(curX < minX)
					minX = curX;
			}
		}
//		System.out.println("Min X = "+minX);
		return minX;
	}
	
	public int getMinZ() {
		Enumeration keys = rooms.keys();
		int minZ = 0;
		int curZ = 0;
		if(keys.hasMoreElements()) {
			minZ = ((Coordinate)keys.nextElement()).getZ();
			while(keys.hasMoreElements()) {
				curZ = ((Coordinate)keys.nextElement()).getZ();
				if(curZ < minZ)
					minZ = curZ;
			}
		}
//		System.out.println("Min X = "+minX);
		return minZ;
	}

	public void resetLevel(int level) {
		Enumeration keys = rooms.keys();
		Coordinate coord = null;
		while(keys.hasMoreElements()) {
			coord = (Coordinate)keys.nextElement();
			if(coord.getZ() == level)
				removeRoom(coord);
		}
	}

	public void resetVNUM() {
		setValue("nextVNUM", getValue("startVNUM"));
	}

	public void writeWorldFile(File file) {
		FileWrapper wrapper = new FileWrapper(file);
		
		// Prep rooms
		Vector roomList = new Vector();
		Enumeration keys = rooms.keys();
		while(keys.hasMoreElements()) {
			roomList.add(rooms.get(keys.nextElement()));
		}
		
		// Sort rooms by VNUM
		Sorter sorter = new Sorter(roomList, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				if(arg0 instanceof Room && arg1 instanceof Room) {
					Room room1 = (Room)arg0;
					Room room2 = (Room)arg1;
					if(room1.getVNUM() > room2.getVNUM())
						return 1;
					else if(room1.getVNUM() < room2.getVNUM())
						return -1;
					else
						return 0;						
				}
				return -1;
			}
		});
		roomList = sorter.sort(Sorter.QUICK_SORT);
		
		// Write the file
		wrapper.open('w');
		Room curRoom = null;
		Exit curExit = null;
		for(int i = 0; i < roomList.size(); i++) {
			curRoom = (Room)roomList.get(i);
			wrapper.writeString("#"+curRoom.getVNUM()+"\n");
			wrapper.writeString((String)curRoom.getValue("title")+"~\n");
			wrapper.writeString(FileWrapper.wrapString((String)curRoom.getValue("description"), 80, "\n"));
			wrapper.writeString("~\n");
			wrapper.writeString(""+getValue("number")+" "+curRoom.getFlags().toInt()+" "+curRoom.getSectorType());
			if(((Integer)curRoom.getValue("mobLimit")).intValue() != 0) {
				wrapper.writeString(" "+curRoom.getValue("mobLimit"));
			}
			wrapper.writeString("\n");
			
			// Write exits
			for(int dir = Exit.MINIMUM_DIRECTION; dir <= Exit.MAXIMUM_DIRECTION; dir++) {
				curExit = curRoom.getExit(dir);
				if(curExit != null) {
					wrapper.writeString("D"+dir+"\n");
					wrapper.writeString("~\n"+curExit.getKeywords()+"~\n");
					wrapper.writeString(""+curExit.getDoorFlags().toInt()+" "+curExit.getKey());
					wrapper.writeString(" "+getRoom(
							getNeighborLocation(curRoom.getCoordinates(), dir)).getVNUM()+"\n");
				}
			}
			wrapper.writeString("S\n");
		}
		wrapper.close();
	}
	
	public Hashtable getRooms() {
		if(rooms == null) {
			rooms = new Hashtable();
		}
		return rooms;
	}

	public int getRoomCount() {
		return rooms.size();
	}
}
