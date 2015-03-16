package com.mthwate.dominion.editor.state;

import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.editor.NiftyUtils;
import com.mthwate.dominion.graphical.Highlighter;
import com.mthwate.dominion.graphical.state.GraphicalAppState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mthwate
 */
public class SpawnAppState extends EditorAppState {

	private ArrayList<Set2i> spawns;

	public SpawnAppState(ArrayList<Set2i> spawns) {
		this.spawns = spawns;
	}

	@Override
	public void update(float tpf) {
		checkSpawns();
		renderSpawns();
	}

	private void checkSpawns() {
		List<Set2i> remove = new ArrayList<>();
		for (Set2i spawn : spawns) {
			if (!TileStore.validPoint(spawn)) {
				remove.add(spawn);
			}
		}
		spawns.removeAll(remove);
	}

	private void renderSpawns() {
		if (NiftyUtils.isSpawn()) {
			for (Set2i spawn : spawns) {
				eapp.addHighlight(spawn.getX(), spawn.getY(), Highlighter.BLUE);
			}
		}
	}

}
