package com.mthwate.dominion.common.message;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.mthwate.dominion.common.tile.Tile;

/**
 * @author mthwate
 */
@Serializable
public class MapMessage extends AbstractMessage {

	private Tile[][] tiles;

	public MapMessage() {}

	public MapMessage(Tile[][] map) {
		this.tiles = map;
	}

	public Tile[][] getMap() {
		return this.tiles;
	}

}