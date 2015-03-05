package com.mthwate.dominion.common;

import com.jme3.math.Vector3f;
import com.mthwate.datlib.math.Set2i;

/**
 * @author mthwate
 */
public class CoordUtils {

	/**
	 * Converts cartesian coordinates to hexagonal coordinates.
	 * Note that the x values of hexagonal and cartesian coordinates are the same.
	 * 
	 * @param x the cartesian x coordinate
	 * @param y the cartesian y coordinate
	 * @return the hexagonal y coordinate
	 */
	public static int cartesianToHex(int x, int y) {
		return y - (x / 2);
	}

	public static Set2i cartesianToHex(Set2i pos) {
		return new Set2i(pos.getX(), cartesianToHex(pos.getX(), pos.getY()));
	}

	/**
	 * Converts hexagonal coordinates to cartesian coordinates.
	 * Note that the x values of hexagonal and cartesian coordinates are the same.
	 *
	 * @param x the hexagonal x coordinate
	 * @param y the hexagonal y coordinate
	 * @return the cartesian y coordinate
	 */
	public static int hexToCartesian(int x, int y) {
		return y + (x / 2);
	}

	public static Set2i hexToCartesian(Set2i pos) {
		return new Set2i(pos.getX(), hexToCartesian(pos.getX(), pos.getY()));
	}

	public static Vector3f getPosCartesian(int x, int y) {
		return getPosHex(x, CoordUtils.cartesianToHex(x, y));
	}

	public static Vector3f getPosCartesian(Set2i pos) {
		return getPosCartesian(pos.getX(), pos.getY());
	}

	public static Vector3f getPosHex(int x, int y) {
		return new Vector3f(x * 3f / 2f, (float) ((Math.sqrt(3) * y) + (Math.sqrt(3) * x / 2)), 0);
	}

	public static Vector3f getPosHex(Set2i pos) {
		return getPosHex(pos.getX(), pos.getY());
	}

	public static boolean isAdjacentCartesian(Set2i p1, Set2i p2) {
		return isAdjacentHex(cartesianToHex(p1), cartesianToHex(p2));
	}

	public static boolean isAdjacentHex(Set2i p1, Set2i p2) {
		int dx = Math.abs(p1.getX() - p2.getX());
		int dy = Math.abs(p1.getY() - p2.getY());
		return (dx < 2 && dy < 2 && dx + dy < 2);
	}
	
}
