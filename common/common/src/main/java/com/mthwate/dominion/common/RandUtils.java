package com.mthwate.dominion.common;

import com.mthwate.datlib.math.Set2i;
import lombok.extern.java.Log;

import java.util.*;
import java.util.logging.Level;

/**
 * @author mthwate
 */
@Log
public class RandUtils {

	private static Random rand = new Random();

	private static Map<Set2i, Integer> nums = new HashMap<>();

	public static int randInt(Set2i pos, int n) {
		Integer i = nums.get(pos);

		if (i == null) {
			i = Math.abs(rand.nextInt());
			nums.put(pos, i);
			log.log(Level.FINE, "Generated random number {0} for position {1}", new Object[]{i, pos});
		}

		return i % n;
	}

}
