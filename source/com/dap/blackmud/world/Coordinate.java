package com.dap.blackmud.world;

import java.io.Serializable;

public class Coordinate implements Serializable {
	private static final long serialVersionUID = 7581876188954258479L;
	int x = 0;
	int y = 0;
	int z = 0;
	
	public Coordinate() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Coordinate(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Coordinate(Coordinate otherCoordinate) {
		this.x = otherCoordinate.getX();
		this.y = otherCoordinate.getY();
		this.z = otherCoordinate.getZ();
	}

	public String toString() {
		return "["+x+","+y+","+z+"]";
	}
	
	public int hashCode() {
		return toString().hashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Coordinate) {
			Coordinate otherCoord = (Coordinate)obj;
			if(otherCoord.getX() == this.getX() &&
					otherCoord.getY() == this.getY() && 
					otherCoord.getZ() == this.getZ())
				return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}
