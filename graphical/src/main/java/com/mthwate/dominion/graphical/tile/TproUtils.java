package com.mthwate.dominion.graphical.tile;

import com.jme3.material.Material;
import com.mthwate.dominion.common.tile.TileProperties;
import com.mthwate.dominion.graphical.MaterialUtils;

import java.util.Random;

/**
 * @author mthwate
 */
public class TproUtils extends com.mthwate.dominion.common.tile.TproUtils {

	private static Random rand = new Random();

	public static Material getMaterialFace(TileProperties tile) {
		String[] textures = tile.getTextures();
		String texture = textures[rand.nextInt(textures.length)];
		return MaterialUtils.getTexturedMaterial(texture);
	}

	public static Material getMaterialSide(TileProperties tile) {
		String[] sides = tile.getSides();
		String side = sides[rand.nextInt(sides.length)];
		return MaterialUtils.getTexturedMaterial(side);
	}

	public static void load(String name) {
		getProperties(name);
	}
}
