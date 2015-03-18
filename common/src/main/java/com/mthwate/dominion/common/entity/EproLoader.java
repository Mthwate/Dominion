package com.mthwate.dominion.common.entity;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author mthwate
 */
public class EproLoader implements AssetLoader {
	
	@Override
	public EntityProperties load(AssetInfo assetInfo) throws IOException {

		InputStreamReader isr = new InputStreamReader(assetInfo.openStream());
		
		YamlReader yr = new YamlReader(isr);

		yr.getConfig().setPrivateFields(true);

		EntityProperties properties = yr.read(EntityProperties.class);

		yr.close();
		isr.close();
		
		return properties;
	}
	
}
