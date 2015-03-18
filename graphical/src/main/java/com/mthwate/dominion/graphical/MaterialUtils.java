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

	private static AssetManager assetManager;
	
	private static Material matWire;
	
	private static Map<ColorRGBA, Material> highlights = new HashMap<>();

	private static Map<String, Material> materials = new HashMap<>();

	private static Map<String, Material> guis = new HashMap<>();

	public static void init(AssetManager assetManager) {
		MaterialUtils.assetManager = assetManager;
	}

	public static Material getTexturedMaterial(String name) {
		
		Material material = materials.get(name);
		
		if (material == null) {
			material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
			material.setTexture("DiffuseMap", TextureUtils.getTexture("textures/" + name + ".png"));
			material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
			materials.put(name, material);
		}
		
		return material;
	}

	public static Material getWireMaterial() {

		if (matWire == null) {
			matWire = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			matWire.setColor("Color", ColorRGBA.Black);
		}

		return matWire;
	}

	public static Material getHighlightMaterial(ColorRGBA color) {

		Material highlight = highlights.get(color);

		if (highlight == null) {
			highlight = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			highlight.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
			highlight.setColor("Color", color);
		}

		return highlight;
	}

	public static Material getGuiMaterial(String name) {

		Material gui = guis.get(name);

		if (gui == null) {
			gui = new Material(assetManager, "Common/MatDefs/Gui/Gui.j3md");
			gui.setColor("Color", ColorRGBA.White);
			gui.setTexture("Texture", TextureUtils.getTexture("textures/" + name + ".png"));
			gui.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
			guis.put(name, gui);
		}

		return gui;
	}
	
}
