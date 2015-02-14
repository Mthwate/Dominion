package com.mthwate.dominion.common;

import com.jme3.math.Vector3f;

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

	public static Vector3f getPosCartesian(int x, int y) {
		return getPosHex(x, com.mthwate.dominion.common.CoordUtils.cartesianToHex(x, y));
	}

	public static Vector3f getPosHex(int x, int y) {
		return new Vector3f(x * 3f / 2f, (float) ((Math.sqrt(3) * y) + (Math.sqrt(3) * x / 2)), 0);
	}
	
}
