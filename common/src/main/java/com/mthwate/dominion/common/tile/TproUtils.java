package com.mthwate.dominion.common.tile;

import com.jme3.asset.AssetManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class TproUtils {

	private static Map<String, TileProperties> properties = new HashMap<String, TileProperties>();

	public static TileProperties getProperties(String name, AssetManager assetManager) {

		TileProperties tpro = properties.get(name);

		if (tpro == null) {
			tpro = (TileProperties) assetManager.loadAsset("tiles/" + name + ".tpro");
			tpro.setName(name);
			properties.put(name, tpro);
		}

		return tpro;
	}

}
