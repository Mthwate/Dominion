package com.mthwate.dominion.server.state;

import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.Path;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.MapMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.common.message.TileMessage;
import com.mthwate.dominion.common.tile.Tile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class PathAppState extends ServerAppState {

	private static Map<Path, Float> paths = new HashMap<>();

	public static void add(Path path) {
		paths.put(path, 0F);
	}

	@Override
	public void update(float tpf) {
		for (Map.Entry<Path, Float> path : paths.entrySet()) {
			path.setValue(path.getValue() + tpf);

			int val = 1;//TODO change this to reflect the unit and the tile being traveled

			if (path.getValue() >= val) {
				path.setValue(path.getValue() - val);

				Set2i cPos = path.getKey().getCurrent();
				Set2i nPos = path.getKey().getNext();

				Tile current = TileStore.get(cPos);
				Tile next = TileStore.get(nPos);

				if (current.hasInhabitant() && !next.hasInhabitant()) {
					if (current.getInhabitant().getType().isMoveable()) {
						next.setInhabitant(current.getInhabitant());
						current.setInhabitant(null);
					}
				}

				path.getKey().step();

				MessageUtils.broadcast(server, new TileMessage(current, cPos.getX(), cPos.getY()));
				MessageUtils.broadcast(server, new TileMessage(next, nPos.getX(), nPos.getY()));

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
