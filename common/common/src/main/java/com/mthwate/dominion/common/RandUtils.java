package com.mthwate.dominion.common;

import com.mthwate.datlib.math.Set2i;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author mthwate
 */
@Slf4j
public class RandUtils {

	private static Random rand = new Random();

	private static Map<Set2i, Integer> nums = new HashMap<>();

	public static int randInt(Set2i pos, int n) {
		Integer i = nums.get(pos);

		if (i == null) {
			i = Math.abs(rand.nextInt());
			nums.put(pos, i);
			log.trace("Generated random number {} for position {}", i, pos);
		}

		return i % n;
	}

}
