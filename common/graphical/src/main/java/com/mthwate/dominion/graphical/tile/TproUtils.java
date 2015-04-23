package com.mthwate.dominion.graphical.tile;

import com.jme3.material.Material;
import com.mthwate.datlib.math.set.Set2i;
import com.mthwate.dominion.common.RandUtils;
import com.mthwate.dominion.common.tile.TileProperties;
import com.mthwate.dominion.graphical.MaterialUtils;

/**
 * @author mthwate
 */
public class TproUtils extends com.mthwate.dominion.common.tile.TproUtils {

	public static Material getMaterialFace(TileProperties tile, Set2i pos) {
		String[] textures = tile.getTextures();
		String texture = textures[RandUtils.randInt(pos, textures.length)];
		return MaterialUtils.getTexturedMaterial(texture);
	}

	public static Material getMaterialSide(TileProperties tile, Set2i pos) {
		String[] sides = tile.getSides();
		String side = sides[RandUtils.randInt(pos, sides.length)];
		return MaterialUtils.getTexturedMaterial(side);
	}

	public static void load(String name) {
		getProperties(name);
	}
}
