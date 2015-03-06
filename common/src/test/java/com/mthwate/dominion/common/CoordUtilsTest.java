package com.mthwate.dominion.common;

import com.mthwate.datlib.math.Set2i;
import junit.framework.TestCase;
import org.junit.Assert;

public class CoordUtilsTest extends TestCase {

	public void testIsAdjacentCartesian() throws Exception {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				int size = 3;
				for (int ix = -size + 1; ix < size; ix++) {
					for (int iy = -size + 1; iy < size; iy++) {
						if (Math.abs(ix + iy) < size) {
							int px = x + ix;
							int py = CoordUtils.hexToCartesian(px, CoordUtils.cartesianToHex(x, y) + iy);
							Set2i source = new Set2i(x, y);
							Set2i dest = new Set2i(px, py);
							if (Math.abs(ix) < 2 && Math.abs(iy) < 2 && Math.abs(ix + iy) < 2 && (ix != 0 || iy != 0)) {
								Assert.assertTrue(CoordUtils.isAdjacentCartesian(source, dest));
							} else {
								Assert.assertFalse(CoordUtils.isAdjacentCartesian(source, dest));
							}
						}
					}
				}
			}
		}
	}
}