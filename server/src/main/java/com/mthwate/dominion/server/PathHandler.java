package com.mthwate.dominion.server;

import com.jme3.network.Server;
import com.mthwate.dominion.common.Path;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.MapMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.common.message.MoveMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class PathHandler {

	private static Map<Path, Float> paths = new HashMap<>();

	public static void add(Path path) {
		paths.put(path, 0F);
	}

	public static void update(Server server, float tpf) {
		for (Map.Entry<Path, Float> path : paths.entrySet()) {
			path.setValue(path.getValue() + tpf);

			int val = 1;//TODO change this to reflect the unit and the tile being traveled

			if (path.getValue() >= val) {
				path.setValue(path.getValue() - val);

				Tile current = TileStore.get(path.getKey().getCurrent());
				Tile next = TileStore.get(path.getKey().getNext());

				if (current.hasInhabitant() && !next.hasInhabitant()) {
					if (current.getInhabitant().getType().moveable) {
						next.setInhabitant(current.getInhabitant());
						current.setInhabitant(null);
					}
				}

				path.getKey().step();

				MessageUtils.broadcast(server, new MapMessage(TileStore.get()));

			}
		}


		Path[] array = new Path[paths.size()];
		paths.keySet().toArray(array);
		for (Path path : array) {
			if (!path.isValid()) {
				paths.remove(path);
			}
		}
	}

}
