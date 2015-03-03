package com.mthwate.dominion.common.entity;

import com.jme3.asset.AssetManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class EproUtils {
	
	private static Map<String, EntityProperties> properties = new HashMap<String, EntityProperties>();
	
	public static EntityProperties getProperties(String name, AssetManager assetManager) {

		EntityProperties epro = properties.get(name);
		
		if (epro == null) {
			epro = (EntityProperties) assetManager.loadAsset("entities/" + name + ".epro");
			properties.put(name, epro);
		}
		
		return epro;
	}
	
}
