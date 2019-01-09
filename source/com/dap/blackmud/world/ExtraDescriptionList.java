package com.dap.blackmud.world;

import java.io.Serializable;
import java.util.Vector;

public class ExtraDescriptionList implements Serializable {
	private static final long serialVersionUID = -3159893163276950419L;
	Vector descriptions = null;
	
	public ExtraDescriptionList() {
		descriptions = new Vector();
	}
	
	public ExtraDescriptionList(ExtraDescriptionList baseList) {
		descriptions = new Vector(baseList.getDescriptions());
	}

	public void addDescription(Description desc) {
		descriptions.add(desc);
	}
	
	public int getDescriptionCount() {
		return descriptions.size();
	}
	
	public Vector getDescriptions() {
		return descriptions;
	}
}
