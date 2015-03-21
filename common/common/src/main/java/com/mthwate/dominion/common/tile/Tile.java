package com.mthwate.dominion.common.tile;

import com.mthwate.dominion.common.entity.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mthwate
 */
@com.jme3.network.serializing.Serializable
public class Tile implements java.io.Serializable {

	@Getter @Setter private TileProperties type;

	@Getter @Setter private int elevation;
	
	@Getter @Setter private Entity inhabitant;

	/**
	 * Serialization constructor.
	 * DO NOT USE!
	 */
	public Tile() {}

	public Tile(TileProperties type) {
		this(type, 0);
	}
	
	public Tile(TileProperties type, int elevation) {
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
