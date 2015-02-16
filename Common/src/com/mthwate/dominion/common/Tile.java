package com.mthwate.dominion.common;

import java.io.Serializable;

/**
 * @author mthwate
 */
@com.jme3.network.serializing.Serializable
public class Tile implements Serializable {
	
	private String type;
	
	private int elevation;
	
	@Deprecated
	public Tile() {}
	
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
