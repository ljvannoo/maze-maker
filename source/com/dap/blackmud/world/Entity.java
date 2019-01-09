package com.dap.blackmud.world;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

import com.dap.blackmud.utils.Constants;


public abstract class Entity implements Serializable {

	private static final long serialVersionUID = -5703141650022465845L;
	
	private Hashtable variables = null;

	public Entity() {
		variables = new Hashtable();
	}
	
	public boolean isKnownField(String field) {
		if(variables.containsKey(field))
			return true;
		return false;
	}
	
	public Object getValue(String field) {
		return variables.get(field);
	}
	
	public int setValue(String field, Object value) {
		if(isKnownField(field)) {
			if(validType(field, value)) {
				variables.put(field, value);
				return Constants.E_SUCCESS;
			} else {
				return Constants.E_INVALID_TYPE;
			}
		}
		return Constants.E_UNKNOWN_FIELD;
	}
	
	public String[] getFieldList() {
		String[] fields = new String[variables.size()];
		Enumeration fieldEnum = variables.keys();
		for(int i = 0; (i < fields.length && fieldEnum.hasMoreElements()); i++) {
			fields[i] = (String)fieldEnum.nextElement();
		}
		
		return fields;
	}
	
	protected void addField(String fieldName, Object value) {
		variables.put(fieldName, value);
	}
	
	public boolean validType(String field, Object value) {
		if(value.getClass().getName().equals(variables.get(field).getClass().getName()))
			return true;
		return false;
	}
}
