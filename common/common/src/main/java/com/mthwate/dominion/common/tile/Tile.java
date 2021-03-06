package com.mthwate.dominion.common.tile;

import com.mthwate.dominion.common.entity.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mthwate
 */
@com.jme3.network.serializing.Serializable
public class Tile implements java.io.Serializable {

	private static final long serialVersionUID = 6211327075280365043L;

	@Setter private String type;

	@Getter @Setter private int elevation;
	
	@Getter @Setter private Entity inhabitant;

	/**
	 * Serialization constructor.
	 * DO NOT USE!
	 */
	public Tile() {}

	public Tile(String type) {
		this(type, 0);
	}
	
	public Tile(String type, int elevation) {
		this.type = type;
		this.elevation = elevation;
	}

	public TileProperties getType() {
		return TproUtils.getProperties(type);
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
