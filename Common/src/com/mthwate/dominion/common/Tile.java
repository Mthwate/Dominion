package com.mthwate.dominion.common;

import java.io.Serializable;

/**
 * @author mthwate
 */

public class Tile implements Serializable {
	
	private String type;
	
	private int elevation;
	
	public Tile(String type, int elevation) {
		this.type = type;
		this.elevation = elevation;
	}
	
	public String getType() {
		return this.type;
	}

	public int getElevation() {
		return this.elevation;
	}
	
}
