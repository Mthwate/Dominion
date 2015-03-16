package com.mthwate.dominion.editor.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.editor.EditorApp;
import com.mthwate.dominion.editor.NiftyUtils;
import com.mthwate.dominion.graphical.KeyControl;
import com.mthwate.dominion.graphical.highlight.HighlightColors;
import com.mthwate.dominion.graphical.highlight.HighlightUtils;
import com.mthwate.dominion.graphical.state.MouseAppState;

/**
 * @author mthwate
 */
public class BrushAppState extends MouseAppState {

	private Node node = new Node();

	private EditorApp eapp;

	private AssetManager assetManager;

	public BrushAppState(Node parentNode) {
		parentNode.attachChild(node);
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		eapp = (EditorApp) app;
		assetManager = app.getAssetManager();
	}

	@Override
	public void update(float tpf) {
		node.detachAllChildren();
		super.update(tpf);
	}

	@Override
	protected void update(float tpf, Set2i pos, boolean clicked) {
		int x = pos.getX();
		int y = pos.getY();

		if (NiftyUtils.isSpawn()) {
			HighlightUtils.highlightTile(x, y, HighlightColors.YELLOW, node, assetManager);
			if (clicked) {
				keyHandler.onAction(KeyControl.CLICK.getName(), false, 0);
				eapp.toggleSpawn(new Set2i(x, y));
			}
		} else {
			String type = NiftyUtils.getTileSelection();

			int elevation = NiftyUtils.getMenuInt("elevation");

			int size = NiftyUtils.getMenuInt("brushSize");


			for (int ix = -size + 1; ix < size; ix++) {
				for (int iy = -size + 1; iy < size; iy++) {
					if (Math.abs(ix + iy) < size) {
						int px = x + ix;
						int py = CoordUtils.hexToCartesian(px, CoordUtils.cartesianToHex(x, y) + iy);
						if (TileStore.validPoint(px, py)) {

							HighlightUtils.highlightTile(px, py, HighlightColors.YELLOW, node, assetManager);

							if (clicked) {

								Tile tile = TileStore.get(px, py);

								String newType = type;
								int newElevation = elevation;

								if (type.equals("")) {
									newType = tile.getType();
								}

								if (NiftyUtils.isRelative("elevation")) {
									newElevation += tile.getElevation();
								}

								newElevation = Math.max(newElevation, 0);

								if (!tile.getType().equals(newType)) {
									tile.setType(newType);
								}

								if (tile.getElevation() != newElevation) {
									TileStore.get(px, py).setElevation(newElevation);
								}
							}
						}
					}
				}
			}
		}
	}

}
