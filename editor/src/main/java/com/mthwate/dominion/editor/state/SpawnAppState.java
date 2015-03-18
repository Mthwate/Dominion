package com.mthwate.dominion.editor.state;

import com.jme3.scene.Node;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.editor.NiftyUtils;
import com.mthwate.dominion.editor.SpawnStore;
import com.mthwate.dominion.graphical.highlight.HighlightColors;
import com.mthwate.dominion.graphical.highlight.HighlightUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mthwate
 */
public class SpawnAppState extends EditorAppState {

	private Node node = new Node();

	public SpawnAppState(Node parentNode) {
		parentNode.attachChild(node);
	}

	@Override
	public void update(float tpf) {
		node.detachAllChildren();
		checkSpawns();
		renderSpawns();
	}

	private void checkSpawns() {
		List<Set2i> remove = new ArrayList<>();
		for (Set2i spawn : SpawnStore.get()) {
			if (!TileStore.validPoint(spawn)) {
				remove.add(spawn);
			}
		}
		SpawnStore.removeAll(remove);
	}

	private void renderSpawns() {
		if (NiftyUtils.isSpawn()) {
			for (Set2i spawn : SpawnStore.get()) {
				HighlightUtils.highlightTile(spawn.getX(), spawn.getY(), HighlightColors.BLUE, node);
			}
		}
	}

}
