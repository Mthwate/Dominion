package com.mthwate.dominion.common;

import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.tile.Tile;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mthwate
 */
public class TileStore {

	@Getter
	@Setter
	private static Tile[][] tiles;

	public static void resize(int x, int y) {
		Tile[][] newTiles = new Tile[x][y];

		for (int xi = 0; xi < x; xi++) {
			for (int yi = 0; yi < y; yi++) {
				if (xi < sizeX() && yi < sizeY()) {
					newTiles[xi][yi] = tiles[xi][yi];
				} else {
					newTiles[xi][yi] = new Tile("null");
				}
			}
		}
		
		tiles = newTiles;
	}

	public static void set(Tile tile, int x, int y) {
		tiles[x][y] = tile;
	}

	public static void set(Tile tile, Set2i pos) {
		set(tile, pos.getX(), pos.getY());
	}
	
	public static Tile get(int x, int y) {
		return tiles[x][y];
	}
	
	public static Tile get(Set2i pos) {
		return get(pos.getX(), pos.getY());
	}
	
	public static int sizeX() {
		int size = 0;
		
		if (tiles != null) {
			size = tiles.length;
		}
		
		return size;
	}

	public static int sizeY() {
		int size = 0;

		if (tiles != null) {
			size = tiles[0].length;
		}

		return size;
	}

	public static boolean validPoint(Set2i pos) {
		return validPoint(pos.getX(), pos.getY());
	}

	public static boolean validPoint(int x, int y) {
		boolean valid = true;
		if (x >= sizeX() || x < 0) {
			valid = false;
		}
		if (y >= sizeY() || y < 0) {
			valid = false;
		}
		return valid;
	}
	
}
