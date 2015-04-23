package com.mthwate.dominion.common;

import com.jme3.network.serializing.Serializable;
import com.mthwate.datlib.math.set.Set2i;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mthwate
 */
@Slf4j
@Serializable
public class Path {

	@Getter @Setter private transient double time = 0;

	List<SerializableSet2i> list = new ArrayList<>();

	@Deprecated
	public Path() {}

	public Path(Set2i start) {
		log.info("Initializing path at " + start);
		list.add(new SerializableSet2i(start));
	}

	/**
	 * Adds an adjacent position to the path.
	 *
	 * @param pos the position to add
	 */
	public void add(Set2i pos) {
		log.info("Adding " + pos + " to path");
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

		if (list.size() < 2) {
			valid = false;
		}

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

	public Set2i getLast() {
		return list.get(list.size() - 1);
	}

	public void step() {
		list.remove(0);
	}
}
