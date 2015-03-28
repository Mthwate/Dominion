package com.mthwate.dominion.graphical;

import com.jme3.asset.AssetManager;
import com.jme3.texture.Texture;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
@Slf4j
public class TextureUtils {

	private static AssetManager assetManager;
	
	private static Map<String, Texture> textures = new HashMap<>();

	public static void init(AssetManager assetManager) {
		TextureUtils.assetManager = assetManager;
	}

	public static Texture getTexture(String name) {
		
		Texture texture = textures.get(name);
		
		if (texture == null) {
			log.info("Loading texture " + name + " for the first time");
			texture = assetManager.loadTexture("textures/" + name + ".png");
			texture.setWrap(Texture.WrapMode.Repeat);
			texture.setMinFilter(Texture.MinFilter.BilinearNoMipMaps);
			texture.setMagFilter(Texture.MagFilter.Nearest);
			textures.put(name, texture);
		}
		
		return texture;
	}
	
}
