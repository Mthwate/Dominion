package com.mthwate.dominion.editor;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.mthwate.datlib.Pair;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class LightUtils {
	
	private static Map<Integer, ColorRGBA> aColors = new LinkedHashMap<Integer, ColorRGBA>();

	private static Map<Integer, ColorRGBA> dColors = new LinkedHashMap<Integer, ColorRGBA>();
	
	static {
		/*
		aColors.put(0, new ColorRGBA(0, 0, 0, 0.1f));
		aColors.put(17, new ColorRGBA(18, 18, 30, 0.057f));
		aColors.put(129, new ColorRGBA(239, 213, 200, 0f));
		aColors.put(161, new ColorRGBA(125, 72, 157, 0.1f));
		aColors.put(52, new ColorRGBA(128, 94, 226, 0.4f));
		aColors.put(148, new ColorRGBA(251, 101, 139, 0.7f));
		aColors.put(37, new ColorRGBA(0, 7, 124, 0.9f));
		aColors.put(104, new ColorRGBA(228, 253, 224, 0.7f));
		aColors.put(189, new ColorRGBA(0, 0, 0, 0.3f));
		aColors.put(62, new ColorRGBA(155, 143, 245, 0.4f));
		aColors.put(174, new ColorRGBA(37, 25, 61, 0.3f));
		aColors.put(79, new ColorRGBA(102, 220, 253, 0.1f));
		*/

		/*
		dColors.put(0, new ColorRGBA(0, 2, 56, 0.1f));
		dColors.put(17, new ColorRGBA(17, 14, 66, 0.49f));
		dColors.put(37, new ColorRGBA(14, 12, 65, 15.4f));
		dColors.put(52, new ColorRGBA(107, 45, 139, 17.1f));
		dColors.put(62, new ColorRGBA(247, 87, 23, 3f));
		dColors.put(79, new ColorRGBA(243, 193, 144, 0.1f));
		dColors.put(104, new ColorRGBA(255, 189, 115, 9.1f));
		dColors.put(129, new ColorRGBA(255, 131, 43, 3.2f));
		dColors.put(148, new ColorRGBA(245, 85, 11, 19f));
		dColors.put(161, new ColorRGBA(165, 4, 103, 7.4f));
		dColors.put(174, new ColorRGBA(55, 1, 74, 0.4f));
		dColors.put(189, new ColorRGBA(0, 2, 56, 10.3f));
*/


		//TODO improve color values
		
		aColors.put(0, new ColorRGBA(255, 200, 255, 0.5f));
		aColors.put(1, new ColorRGBA(255, 200, 255, 0.5f));
		aColors.put(4, new ColorRGBA(255, 255, 255, 1));
		aColors.put(7, new ColorRGBA(255, 200, 255, 0.5f));
		aColors.put(8, new ColorRGBA(255, 200, 255, 0.5f));

		dColors.put(0, new ColorRGBA(255, 100, 0, 0));
		dColors.put(1, new ColorRGBA(255, 100, 0, 0));
		dColors.put(4, new ColorRGBA(255, 255, 255, 1));
		dColors.put(7, new ColorRGBA(255, 100, 0, 0));
		dColors.put(8, new ColorRGBA(255, 100, 0, 0));

	}
	
	public static void updateSun(DirectionalLight light, DirectionalLightShadowRenderer dlsr, Clock clock) {

		float x = clock.getRatio() * -2 + 1;

		light.setDirection(new Vector3f(x, 0, -(1-Math.abs(x))));
		
		setLightColor(light, clock, dColors);
		
		float intensity = light.getColor().getAlpha();
		
		dlsr.setShadowIntensity(intensity);
	}
	
	public static void updateAmbient(AmbientLight light, Clock clock) {
		setLightColor(light, clock, aColors);
	}
	
	private static void setLightColor(Light light, Clock clock, Map<Integer, ColorRGBA> colors) {


		Integer[] indexes = colors.keySet().toArray(new Integer[0]);

		int last = indexes[colors.size()-1];

		float pos = clock.getRatio() * last;


		ColorRGBA color = null;

		if (pos == (int) pos) {
			ColorRGBA jcol = colors.get((int) pos);
			color = new ColorRGBA(jcol.getRed() / 256f, jcol.getGreen() / 256f, jcol.getBlue() / 256f, 1);
		} else {
			int i = 0;
			while (indexes[i] < pos) {
				i++;
			}
			int low = indexes[i-1];
			int high = indexes[i];

			int diff = high - low;

			ColorRGBA lowColor = colors.get(low);
			ColorRGBA highColor = colors.get(high);

			float r = highColor.getRed() - lowColor.getRed();
			float g = highColor.getGreen() - lowColor.getGreen();
			float b = highColor.getBlue() - lowColor.getBlue();
			float a = highColor.getAlpha() - lowColor.getAlpha();

			r = lowColor.getRed() + (r * (pos - low) / diff);
			g = lowColor.getGreen() + (g * (pos - low) / diff);
			b = lowColor.getBlue() + (b * (pos - low) / diff);
			a = lowColor.getAlpha() + (a * (pos - low) / diff);

			color = new ColorRGBA(r / 256f, g / 256f, b / 256f, 1).mult(a);
		}

		light.setColor(color);
	}
	
}
