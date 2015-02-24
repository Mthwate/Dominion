package com.mthwate.dominion.graphical;

import com.mthwate.dominion.common.CoordUtils;
import org.junit.Assert;
import org.junit.Test;

public class CoordUtilsTest {

	@Test
	public void testReversible() throws Exception {
		int range = 10;
		
		for (int x = -range; x <= range; x++) {
			for (int y = -range; y <= range; y++) {
				Assert.assertEquals(y, CoordUtils.cartesianToHex(x, CoordUtils.hexToCartesian(x, y)));
				Assert.assertEquals(y, CoordUtils.hexToCartesian(x, CoordUtils.cartesianToHex(x, y)));
			}
		}
	}
}