package com.mthwate.dominion.common.message;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.mthwate.datlib.math.set.Set2i;
import com.mthwate.dominion.common.tile.Tile;

/**
 * @author mthwate
 */
@Serializable
public class TileMessage extends AbstractMessage {
	
	private Tile tile;

	private int x;

	private int y;

	public TileMessage() {}
	
	public TileMessage(Tile tile, int x, int y) {
		this.tile = tile;
		this.x = x;
		this.y = y;
	}

	public Tile getTile() {
		return this.tile;
	}

	public Set2i getPos() {
		return new Set2i(x, y);
	}
	
}
