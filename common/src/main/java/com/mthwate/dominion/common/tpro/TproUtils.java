package com.mthwate.dominion.common.tpro;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.mthwate.dominion.common.MaterialUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author mthwate
 */
public class TproUtils {

	private static Random rand = new Random();
	
	private static Map<String, TileProperties> properties = new HashMap<String, TileProperties>();
	
	private static TileProperties getProperties(String name, AssetManager assetManager) {
		
		TileProperties tpro = properties.get(name);
		
		if (tpro == null) {
			tpro = (TileProperties) assetManager.loadAsset("tiles/" + name + ".tpro");
			properties.put(name, tpro);
		}
		
		return tpro;
	}

	public static Material getMaterialFace(String name, AssetManager assetManager) {
		String[] textures = getProperties(name, assetManager).textures;
		String texture = textures[rand.nextInt(textures.length)];
		return MaterialUtils.getTexturedMaterial(texture, assetManager);
	}

	public static Material getMaterialSide(String name, AssetManager assetManager) {
		String[] sides = getProperties(name, assetManager).sides;
		String side = sides[rand.nextInt(sides.length)];
		return MaterialUtils.getTexturedMaterial(side, assetManager);
	}
	
}
