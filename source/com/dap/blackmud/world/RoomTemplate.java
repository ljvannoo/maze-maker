package com.dap.blackmud.world;

import java.io.Serializable;

import com.dap.blackmud.utils.Constants;


public class RoomTemplate implements Serializable{
	private static final long serialVersionUID = -4415475219785655352L;
	
	private Room baseRoom = null;
	private int id = Constants.UNDEFINED_INT;
	
	public RoomTemplate(int id, Room baseRoom) {
		this.id = id;
		this.baseRoom = new Room(baseRoom);
	}
	
	public String toString() {
		return baseRoom.getValue("title").toString();
	}

	public Room getRoom() {
		return baseRoom;
	}

	public int getID() {
		return id;
	}
}
