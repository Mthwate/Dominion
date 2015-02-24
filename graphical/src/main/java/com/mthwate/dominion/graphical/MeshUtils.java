package com.mthwate.dominion.graphical;

import com.jme3.scene.Mesh;
import com.mthwate.dominion.graphical.mesh.HexLine;
import com.mthwate.dominion.graphical.mesh.HexSides;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mthwate
 */
public class MeshUtils {

	private static List<HexLine> wires = new ArrayList<HexLine>();

	private static List<HexSides> sides = new ArrayList<HexSides>();

	public static Mesh getWire(int z, float elevMod) {

		while (wires.size() <= z) {
			wires.add(new HexLine(1, wires.size() * elevMod));
		}

		return wires.get(z);
	}

	public static HexSides getSide(int z, float elevMod) {

		while (sides.size() <= z) {
			sides.add(new HexSides(1, sides.size() * elevMod));
		}

		return sides.get(z);
	}

}
