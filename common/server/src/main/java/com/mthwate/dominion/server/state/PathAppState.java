package com.mthwate.dominion.server.state;

import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.MapUtils;
import com.mthwate.dominion.common.Path;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.entity.Entity;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.common.message.TileMessage;
import com.mthwate.dominion.common.tile.Tile;

/**
 * @author mthwate
 */
public class PathAppState extends ServerAppState {

	@Override
	public void update(float tpf) {
		for (Entity entity : MapUtils.getAllInhabitants()) {

			Path path = entity.getPath();

			if (path != null) {

				path.setTime(path.getTime() + tpf);

				int val = 1;//TODO change this to reflect the unit and the tile being traveled

				if (path.getTime() >= val) {
					path.setTime(path.getTime() - val);

					Set2i cPos = path.getCurrent();
					Set2i nPos = path.getNext();

					Tile current = TileStore.get(cPos);
					Tile next = TileStore.get(nPos);

					if (current.hasInhabitant() && !next.hasInhabitant()) {
						if (entity == current.getInhabitant()) {
							next.setInhabitant(entity);
							current.setInhabitant(null);
						}
					}



					MessageUtils.broadcast(sapp.getServer(), new TileMessage(current, cPos.getX(), cPos.getY()));
					MessageUtils.broadcast(sapp.getServer(), new TileMessage(next, nPos.getX(), nPos.getY()));



					path.step();

					if (!path.isValid()) {
						entity.setPath(null);
					}

				}
			}
		}
	}

}
