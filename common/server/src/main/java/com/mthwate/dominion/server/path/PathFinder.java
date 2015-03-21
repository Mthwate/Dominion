package com.mthwate.dominion.server.path;

import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.TileStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A very poorly implemented path finder.
 * DO NOT USE.
 * Only to be used as a reference
 *
 * @author mthwate
 */
@Deprecated
public class PathFinder {

	private List<Set2i> unvisited = new ArrayList<Set2i>();

	private Map<Set2i, Integer> visited = new HashMap<Set2i, Integer>();

	private List<Set2i> check = new ArrayList<Set2i>();

	public PathFinder(Set2i start) {

		int sx = TileStore.sizeX();
		int sy = TileStore.sizeY();

		for (int ix = 0; ix < sx; ix++) {
			for (int iy = 0; iy < sy; iy++) {
				unvisited.add(new Set2i(ix, iy));
			}
		}

		unvisited.remove(start);
		visited.put(start, 0);
		check.add(start);
	}

	public void run() {
		while (!check.isEmpty()) {
			tmp(check.get(0));
		}
	}

	private void tmp(Set2i pos) {

		for (Set2i adj : getAdjacent(pos)) {
			if (visited.containsKey(adj)) {
				if (visited.get(adj) > visited.get(pos) + tileCost(adj)) {
					visited.put(adj, visited.get(pos) + tileCost(adj));
					check.add(adj);
				}
				if (visited.get(pos) > visited.get(adj) + tileCost(pos)) {
					visited.put(pos, visited.get(adj) + tileCost(pos));
				}
			} else if (unvisited.contains(adj)) {
				unvisited.remove(adj);
				visited.put(adj, visited.get(pos) + tileCost(adj));
				check.add(adj);
			}
		}

		check.remove(pos);
	}

	private List<Set2i> getAdjacent(Set2i pos) {
		List<Set2i> adj = new ArrayList<Set2i>();
		for (int ix = -1; ix <= 1; ix++) {
			for (int iy = -1; iy <= 1; iy++) {
				if (ix != iy) {
					Set2i newPos = CoordUtils.hexToCartesian(CoordUtils.cartesianToHex(pos).addNew(ix, iy));
					if (TileStore.validPoint(newPos)) {
						adj.add(newPos);
					}
				}
			}
		}
		return adj;
	}

	private int tileCost(Set2i pos) {
		int a = 1;
		if (pos.getX() == 12 && pos.getY() != 20) {
			a = 30;
		}
		return a;//TODO change this
	}

}
