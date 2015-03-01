package com.mthwate.dominion.graphical.assets;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.system.JmeSystem;
import com.mthwate.dominion.common.ListLoader;
import com.mthwate.dominion.graphical.tpro.TproLoader;
import com.mthwate.dominion.graphical.tpro.TproUtils;
import org.junit.Test;

import java.util.List;

public class Tile {
	
	@Test
	public void loadTiles() {

		//TODO make a universal way the asset manager is initialized so the same code is used for the application and tests
		
		AssetManager assetManager = JmeSystem.newAssetManager();

		assetManager.registerLocator("src/main/resources", FileLocator.class);

		assetManager.registerLoader(ListLoader.class, "list");
		assetManager.registerLoader(TproLoader.class, "tpro");
		
		List<String> tiles = (List<String>) assetManager.loadAsset("tiles/tiles.list");

		for (String tile : tiles) {
			TproUtils.load(tile, assetManager);
		}
		
	}
	
}