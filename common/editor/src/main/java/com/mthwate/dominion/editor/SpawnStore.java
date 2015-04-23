package com.mthwate.dominion.editor;

import com.mthwate.datlib.math.set.Set2i;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author mthwate
 */
public class SpawnStore {

	private static List<Set2i> spawns = new ArrayList<>();

	public static List<Set2i> get() {
		return spawns;
	}

	public static void clear() {
		spawns.clear();
	}

	public static void add(Set2i spawn) {
		spawns.add(spawn);
	}

	public static void toggleSpawn(Set2i spawn) {
		if (!spawns.remove(spawn)) {
			spawns.add(spawn);
		}
	}

	public static int size() {
		return spawns.size();
	}

	public static void removeAll(Collection<Set2i> remove) {
		spawns.removeAll(remove);
	}

	public static void toArray(Set2i[] array) {
		spawns.toArray(array);
	}
}
