package com.mthwate.dominion.graphical;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class MaterialUtils {
	
	private static Material matWire;
	
	private static Map<ColorRGBA, Material> highlights = new HashMap<ColorRGBA, Material>();
	
	private static Map<String, Material> materials = new HashMap<String, Material>();

	public static Material getTexturedMaterial(String name, AssetManager assetManager) {
		
		Material material = materials.get(name);
		
		if (material == null) {
			material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
			material.setTexture("DiffuseMap", TextureUtils.getTexture("textures/" + name + ".png", assetManager));
			material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
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

	public static Material getHighlightMaterial(ColorRGBA color, AssetManager assetManager) {

		Material highlight = highlights.get(color);

		if (highlight == null) {
			highlight = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			highlight.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
			highlight.setColor("Color", color);
		}

		return highlight;
	}
	
}
