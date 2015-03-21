package com.mthwate.dominion.editor;

import org.junit.Assert;
import org.junit.Test;

public class ClockTest {

	@Test
	public void testGetRatio() {
		Clock clock = new Clock(60);

		for (int i = 0; i < 12; i++) {
			Assert.assertEquals(clock.getRatio(), (1F / 12) * i, 0.0000001);
			clock.update(5);
		}

		Assert.assertEquals(clock.getRatio(), 0, 0);
	}

	@Test
	public void testGetCycle() {
		for (int i = 0; i < 100; i++) {
			float cycle = i / 10F;
			Clock clock = new Clock(cycle);
			Assert.assertEquals(clock.getCycle(), cycle, 0);
		}
	}

	@Test
	public void testGetProgress() {
		Clock clock = new Clock(10);
		for (int i = 0; i < 100; i++) {
			Assert.assertEquals(clock.getProgress(), 0.1 * i, 0.00001);
			clock.update(0.1F);
		}
	}
}