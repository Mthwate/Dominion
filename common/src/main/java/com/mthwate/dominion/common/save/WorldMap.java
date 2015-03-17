package com.mthwate.dominion.common.save;

import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.tile.Tile;

import java.io.Serializable;

/**
 * @author mthwate
 */
public class WorldMap implements Serializable {

	private final Tile[][] tiles;

	private final Set2i[] spawns;

	public WorldMap(Tile[][] tiles, Set2i[] spawns) {
		this.tiles = tiles;
		this.spawns = spawns;
	}

	public Tile[][] getTiles() {
		return this.tiles;
	}

	public Set2i[] getSpawns() {
		return this.spawns;
	}

}
