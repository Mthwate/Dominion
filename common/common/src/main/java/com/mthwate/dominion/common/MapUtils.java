package com.mthwate.dominion.common;

import com.jme3.system.NanoTimer;
import com.jme3.system.Timer;
import com.mthwate.dominion.common.entity.Entity;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mthwate
 */
@Slf4j
public class MapUtils {

	public static List<Entity> getAllInhabitants() {
		Timer t = new NanoTimer();
		List<Entity> inhabitants = new ArrayList<>();
		for (int x = 0; x < TileStore.sizeX(); x++) {
			for (int y = 0; y < TileStore.sizeY(); y++) {
				if (TileStore.get(x, y).hasInhabitant()) {
					inhabitants.add(TileStore.get(x, y).getInhabitant());
				}
			}
		}
		return inhabitants;
	}

}
