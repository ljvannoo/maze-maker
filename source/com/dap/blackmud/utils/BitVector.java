package com.dap.blackmud.utils;

import java.io.Serializable;

public class BitVector implements Serializable {

	private static final long serialVersionUID = -5152008363902240973L;
	
	private int bits = 0;
	
	public BitVector() {
		bits = 0;
	}
	
	public BitVector(int bits) {
		this.bits = bits;
	}

	public int toInt() {
        return bits;
    }
    
    public void setFromInt(int newVector) {
    	bits = newVector;
    }
    
    public void set(int bit) {
        if((bits&1<<bit) != (1<<bit)) {
        	bits+=1<<bit;
        }
    }
    
    public void clear(int bit) {
        if((bits&1<<bit) == (1<<bit)) {
        	bits-=1<<bit;
        }
    }
    
    public boolean isFullSet(int bit) {
    	if(bit <= 0)
    		return false;
    	
    	if((bits&bit) == bit) {
            return true;
        }
        return false;
    }
    
    public boolean isSet(int bit) {
        if((bits&1<<bit) == (1<<bit)) {
            return true;
        }
        return false;
    }
    
    public void flip(int bit) {
        if((bits&1<<bit) == (1<<bit))
        	bits -= 1<<bit;
        else
        	bits += 1<<bit;
    }
    
    public boolean empty() {
        if(bits == 0)
            return true;
        return false;
    }
    
    public String toString() {
    	return ""+bits;
    }

	public void remove(int bit) {
		if(isFullSet(bit))
			bits -= bit;
	}
}
