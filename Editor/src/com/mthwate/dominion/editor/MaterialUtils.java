package com.mthwate.dominion.editor;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class MaterialUtils {
	
	private static Material matWire;
	
	private static Map<String, Material> materials = new HashMap<String, Material>();
	
	@Deprecated
	public static Material newMaterial(AssetManager assetManager) {
		return new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
	}

	@Deprecated
	public static Texture newTexture(String name, AssetManager assetManager) {
		return TextureUtils.getTexture("textures/" + name + ".png", assetManager);
	}

	public static Material getTexturedMaterial(String name, AssetManager assetManager) {
		
		Material material = materials.get(name);
		
		if (material == null) {
			material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
			material.setTexture("DiffuseMap", TextureUtils.getTexture("textures/" + name + ".png", assetManager));
			materials.put(name, material);
		}
		
		return material;
	}
	
	public static Material getWireMaterial(AssetManager assetManager) {
		
		if (matWire == null) {
			matWire = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			matWire.setColor("Color", ColorRGBA.Black);
		}

		return matWire;
	}
	
}
