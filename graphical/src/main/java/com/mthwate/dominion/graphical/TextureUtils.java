package com.mthwate.dominion.graphical;

import com.jme3.asset.AssetManager;
import com.jme3.texture.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class TextureUtils {

	private static AssetManager assetManager;
	
	private static Map<String, Texture> textures = new HashMap<>();

	public static void init(AssetManager assetManager) {
		TextureUtils.assetManager = assetManager;
	}

	public static Texture getTexture(String path) {
		
		Texture texture = textures.get(path);
		
		if (texture == null) {
			texture = assetManager.loadTexture(path);
			texture.setWrap(Texture.WrapMode.Repeat);
			texture.setMinFilter(Texture.MinFilter.BilinearNoMipMaps);
			texture.setMagFilter(Texture.MagFilter.Nearest);
			textures.put(path, texture);
		}
		
		return texture;
	}
	
}
