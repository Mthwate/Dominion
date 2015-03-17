package com.mthwate.dominion.common.tile;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author mthwate
 */
public class TproLoader implements AssetLoader {
	
	@Override
	public TileProperties load(AssetInfo assetInfo) throws IOException {

		InputStreamReader isr = new InputStreamReader(assetInfo.openStream());
		
		YamlReader yr = new YamlReader(isr);

		TileProperties properties = yr.read(TileProperties.class);

		yr.close();
		isr.close();
		
		return properties;
	}
	
}
