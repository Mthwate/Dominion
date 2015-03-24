package com.mthwate.dominion.common.message;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.mthwate.dominion.common.entity.Entity;
import com.mthwate.dominion.common.tile.Tile;
import com.mthwate.dominion.common.tile.TileProperties;
import com.mthwate.dominion.common.tile.TproUtils;

/**
 * @author mthwate
 */
@Serializable
public class MapMessage extends AbstractMessage {

	private String[][] types;

	private int[][] elevations;

	private Entity[][] inhabitants;

	public MapMessage() {}
	
	public MapMessage(Tile[][] map) {

		int x = map.length;
		int y = map[0].length;

		types = new String[x][y];
		elevations = new int[x][y];
		inhabitants = new Entity[x][y];


		for (int ix = 0; ix < x; ix++) {
			for (int iy = 0; iy < x; iy++) {
				types[ix][iy] = map[ix][iy].getType().getName();
				elevations[ix][iy] = map[ix][iy].getElevation();
				inhabitants[ix][iy] = map[ix][iy].getInhabitant();
			}
		}
	}
	
	public Tile[][] getMap() {

		int x = types.length;
		int y = types[0].length;

		Tile[][] map = new Tile[x][y];

		for (int ix = 0; ix < x; ix++) {
			for (int iy = 0; iy < x; iy++) {
				map[ix][iy] = new Tile(TproUtils.getProperties(types[ix][iy]), elevations[ix][iy]);
				map[ix][iy].setInhabitant(inhabitants[ix][iy]);
			}
		}

		return map;
	}
	
}
