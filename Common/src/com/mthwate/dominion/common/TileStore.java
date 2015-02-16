package com.mthwate.dominion.common;

import com.mthwate.datlib.Vector2i;

/**
 * @author mthwate
 */
public class TileStore {
	
	private static Tile[][] tiles;

	public static void set(Tile[][] newTiles) {
		tiles = newTiles;
	}

	public static void set(Tile tile, int x, int y) {
		tiles[x][y] = tile;
	}

	public static void set(Tile tile, Vector2i pos) {
		set(tile, pos.getX(), pos.getY());
	}

	public static Tile[][] get() {
		return tiles;
	}
	
	public static Tile get(int x, int y) {
		return tiles[x][y];
	}
	
	public static int sizeX() {
		return tiles.length;
	}

	public static int sizeY() {
		return tiles[0].length;
	}
	
}
