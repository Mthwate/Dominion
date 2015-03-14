package com.mthwate.dominion.common;

import com.mthwate.dominion.common.entity.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mthwate
 */
@com.jme3.network.serializing.Serializable
public class Tile implements java.io.Serializable {

	@Getter @Setter private String type;

	@Getter @Setter private int elevation;
	
	@Getter @Setter private Entity inhabitant;

	public Tile() {
		this.type = "null";
		this.elevation = 0;
	}
	
	public Tile(String type, int elevation) {
		this.type = type;
		this.elevation = elevation;
	}
	
	public boolean hasInhabitant() {
		return inhabitant != null;
	}

	@Override
	public Tile clone() {
		Tile clone = new Tile(type, elevation);
		clone.setInhabitant(inhabitant);
		return clone;
	}
}
