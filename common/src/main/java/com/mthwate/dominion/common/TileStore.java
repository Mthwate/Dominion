package com.mthwate.dominion.common;

import com.mthwate.datlib.math.Set2i;

/**
 * @author mthwate
 */
public class TileStore {
	
	private static Tile[][] tiles;

	public static void resize(int x, int y) {
		Tile[][] newTiles = new Tile[x][y];

		for (int xi = 0; xi < x; xi++) {
			for (int yi = 0; yi < y; yi++) {
				if (xi < sizeX() && yi < sizeY()) {
					newTiles[xi][yi] = tiles[xi][yi];
				} else {
					newTiles[xi][yi] = new Tile("null", 0);
				}
			}
		}
		
		tiles = newTiles;
	}

	public static void set(Tile[][] newTiles) {
		tiles = newTiles;
	}

	public static void set(Tile tile, int x, int y) {
		tiles[x][y] = tile;
	}

	public static void set(Tile tile, Set2i pos) {
		set(tile, pos.getX(), pos.getY());
	}

	public static Tile[][] get() {
		return tiles;
	}
	
	public static Tile get(int x, int y) {
		return tiles[x][y];
	}
	
	public static int sizeX() {
		int size = 0;
		
		if (tiles != null) {
			size = tiles.length;
		}
		
		return size;
	}

	public static int sizeY() {
		return tiles[0].length;
	}
	
}
