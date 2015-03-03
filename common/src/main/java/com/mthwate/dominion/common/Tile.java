package com.mthwate.dominion.common;

import com.mthwate.dominion.common.entity.Entity;
import com.mthwate.dominion.common.entity.EntityProperties;

import java.io.Serializable;

/**
 * @author mthwate
 */
@com.jme3.network.serializing.Serializable
public class Tile implements Serializable {
	
	private String type;
	
	private int elevation;
	
	private Entity inhabitant;

	public Tile() {
		this.type = "null";
		this.elevation = 0;
	}
	
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
	
	public Entity getInhabitant() {
		return inhabitant;
	}
	
	public boolean hasInhabitant() {
		return inhabitant != null;
	}
	
	public void setInhabitant(Entity entity) {
		this.inhabitant = entity;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setElevation(int elevation) {
		this.elevation = elevation;
	}

	@Override
	public Tile clone() {
		Tile clone = new Tile(type, elevation);
		clone.setInhabitant(inhabitant);
		return clone;
	}
}
