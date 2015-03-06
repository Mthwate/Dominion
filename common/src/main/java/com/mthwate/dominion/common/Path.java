package com.mthwate.dominion.common;

import com.jme3.network.serializing.Serializable;
import com.mthwate.datlib.math.Set2i;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mthwate
 */
@Serializable
public class Path {

	List<SerializableSet2i> list = new ArrayList<>();

	@Deprecated
	public Path() {}

	public Path(Set2i start) {
		list.add(new SerializableSet2i(start));
	}

	public void add(Set2i pos) {
		if (!CoordUtils.isAdjacentCartesian(list.get(list.size() -1), pos)) {
			throw new IllegalArgumentException("Tiles not adjacent");
		}
		list.add(new SerializableSet2i(pos));
	}

	public Set2i getCurrent() {
		return list.get(0);
	}

	public Set2i getNext() {
		return list.get(1);
	}

	public boolean isValid() {
		boolean valid = true;

		for (Set2i item : list) {
			if (!TileStore.validPoint(item)) {
				valid = false;
			}
		}

		for (int i = 0; i < list.size() - 1; i++) {
			if (!CoordUtils.isAdjacentCartesian(list.get(i), list.get(i+1))) {
				valid = false;
			}
		}

		return valid;
	}

}
