package com.mthwate.dominion.editor.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.editor.EditorApp;
import com.mthwate.dominion.editor.NiftyUtils;
import com.mthwate.dominion.graphical.highlight.HighlightColors;
import com.mthwate.dominion.graphical.highlight.HighlightUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mthwate
 */
public class SpawnAppState extends EditorAppState {

	private Node node = new Node();

	private AssetManager assetManager;

	private ArrayList<Set2i> spawns;

	public SpawnAppState(Node parentNode, ArrayList<Set2i> spawns) {
		parentNode.attachChild(node);
		this.spawns = spawns;
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		assetManager = app.getAssetManager();
	}

	@Override
	public void update(float tpf) {
		node.detachAllChildren();
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
				HighlightUtils.highlightTile(spawn.getX(), spawn.getY(), HighlightColors.BLUE, node, assetManager);
			}
		}
	}

}
