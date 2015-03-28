package com.mthwate.dominion.common.tile;

import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetManager;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
@Slf4j
public class TproUtils {

	private static AssetManager assetManager;

	private static Map<String, TileProperties> properties = new HashMap<>();

	public static void init(AssetManager assetManager) {
		TproUtils.assetManager = assetManager;
	}

	public static TileProperties getProperties(String name) {

		TileProperties tpro = properties.get(name);

		if (tpro == null) {
			log.info("Loading tpro " + name + " for the first time");
			tpro = assetManager.loadAsset(new AssetKey<TileProperties>("tiles/" + name + ".tpro"));
			tpro.setName(name);
			properties.put(name, tpro);
		}

		return tpro;
	}

}
