package com.dap.blackmud.world;

import java.io.Serializable;
import java.util.Vector;

public class ApplyList implements Serializable {
	private static final long serialVersionUID = -4396892269570286884L;
	
	Vector applies = null;
	
	public ApplyList() {
		applies = new Vector();
	}
	
	public ApplyList(ApplyList baseList) {
		applies = new Vector(baseList.getApplies());
	}

	public void addApply(ObjApply apply) {
		applies.add(apply);
	}
	
	public int getApplyCount() {
		return applies.size();
	}
	
	public Vector getApplies() {
		return applies;
	}

}
