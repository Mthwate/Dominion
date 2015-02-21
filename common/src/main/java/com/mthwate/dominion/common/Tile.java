package com.mthwate.dominion.common;

import java.io.Serializable;

/**
 * @author mthwate
 */
@com.jme3.network.serializing.Serializable
public class Tile implements Serializable {
	
	private String type;
	
	private int elevation;
	
	private String inhabitant;//TODO change from string type
	
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
	
	public String getInhabitant() {
		return inhabitant;
	}
	
	public boolean hasInhabitant() {
		return inhabitant != null;
	}
	
	public void setInhabitant(String entity) {
		this.inhabitant = entity;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setElevation(int elevation) {
		this.elevation = elevation;
	}
}
