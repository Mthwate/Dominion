package com.mthwate.dominion.common;

import com.jme3.asset.AssetManager;
import com.jme3.texture.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class TextureUtils {
	
	private static Map<String, Texture> textures = new HashMap<String, Texture>();
	
	public static Texture getTexture(String path, AssetManager assetManager) {
		
		Texture texture = textures.get(path);
		
		if (texture == null) {
			texture = assetManager.loadTexture(path);
			texture.setWrap(Texture.WrapMode.Repeat);
			texture.setMagFilter(Texture.MagFilter.Nearest);
			textures.put(path, texture);
		}
		
		return texture;
	}
	
}
