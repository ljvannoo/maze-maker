package com.dap.blackmud.world;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

import com.dap.blackmud.utils.Constants;


public class RoomTemplateList implements Serializable {
	private static final long serialVersionUID = -8730834972294892680L;
	
	private Hashtable templates = null;
	private int defaultTemplateID = Constants.UNDEFINED_INT;
	private int nextID = 0;
	
	public RoomTemplateList() {
		templates = new Hashtable();
	}

	public int size() {
		return templates.size();
	}

	public RoomTemplate get(int id) {
		return (RoomTemplate)templates.get(new Integer(id));
	}
	
	public RoomTemplate getCurrentTemplate() {
		if(defaultTemplateID == Constants.UNDEFINED_INT)
			return null;
		
		return get(defaultTemplateID);
	}
	
	public boolean isDefaultTemplate(RoomTemplate template) {
		return (template.getID() == defaultTemplateID);
	}

	public RoomTemplate createNewTemplate(Room room) {
		RoomTemplate template = new RoomTemplate(nextID, room);
		nextID++;
		templates.put(new Integer(template.getID()), template);
		if(defaultTemplateID == Constants.UNDEFINED_INT) 
			defaultTemplateID = template.getID();
		return template;
	}

	public Enumeration getTemplateIDs() {
		return templates.keys();
	}

	public void setDefaultID(int id) {
		if(templates.containsKey(new Integer(id)))
			defaultTemplateID = id;
				
	}

	public void remove(int id) {
		if(id == defaultTemplateID) {
			Enumeration ids = getTemplateIDs();
			if(ids.hasMoreElements())
				defaultTemplateID = ((Integer)ids.nextElement()).intValue();
			else
				defaultTemplateID = Constants.UNDEFINED_INT;
		}
		templates.remove(new Integer(id));			
	}

	public RoomTemplate getDefaultTemplate() {
		return get(defaultTemplateID);
	}
	
}
