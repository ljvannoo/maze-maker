package com.dap.blackmud.world;

import java.io.Serializable;

public class MobPosition implements Serializable {
	private static final long serialVersionUID = -1865881341463506268L;
	
	private String name;
	private boolean useable;
	
	public MobPosition(String positionName, boolean useable) {
		this.name = positionName;
		this.useable = useable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUseable() {
		return useable;
	}

	public void setUseable(boolean useable) {
		this.useable = useable;
	}
	
	public String toString() {
		return name;
	}
}
