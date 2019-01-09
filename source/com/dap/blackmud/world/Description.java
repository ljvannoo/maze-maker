package com.dap.blackmud.world;

import java.io.Serializable;

public class Description implements Serializable {
	private static final long serialVersionUID = -8420976349927535068L;

	private String keywords = null;
	private String description = null;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Description(String keywords, String description) {
		this.keywords = keywords;
		this.description = description;
	}
}
