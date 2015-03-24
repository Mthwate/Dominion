package com.mthwate.dominion.editor.state;

import com.jme3.scene.Node;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.tile.Tile;
import com.mthwate.dominion.common.tile.TileProperties;
import com.mthwate.dominion.common.tile.TproUtils;
import com.mthwate.dominion.editor.NiftyUtils;
import com.mthwate.dominion.editor.SpawnStore;
import com.mthwate.dominion.graphical.KeyControl;
import com.mthwate.dominion.graphical.highlight.HighlightColors;
import com.mthwate.dominion.graphical.highlight.HighlightUtils;
import com.mthwate.dominion.graphical.state.MouseAppState;

/**
 * @author mthwate
 */
public class BrushAppState extends MouseAppState {

	private Node node = new Node();

	public BrushAppState(Node parentNode) {
		parentNode.attachChild(node);
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
			HighlightUtils.highlightTile(x, y, HighlightColors.YELLOW, node);
			if (clicked) {
				keyHandler.unpress(KeyControl.CLICK);
				SpawnStore.toggleSpawn(new Set2i(x, y));
			}
		} else {
			String type = NiftyUtils.getTileSelection();

			int elevation = NiftyUtils.getMenuInt("elevation");

			int rotation = NiftyUtils.getMenuInt("rotation");

			int size = NiftyUtils.getMenuInt("brushSize");


			for (int ix = -size + 1; ix < size; ix++) {
				for (int iy = -size + 1; iy < size; iy++) {
					if (Math.abs(ix + iy) < size) {
						int px = x + ix;
						int py = CoordUtils.hexToCartesian(px, CoordUtils.cartesianToHex(x, y) + iy);
						if (TileStore.validPoint(px, py)) {

							HighlightUtils.highlightTile(px, py, HighlightColors.YELLOW, node);

							if (clicked) {

								Tile tile = TileStore.get(px, py);

								TileProperties newType = tile.getType();
								int newElevation = elevation;

								if (!type.equals("")) {
									newType = TproUtils.getProperties(type);
								}

								if (NiftyUtils.isRelative("elevation")) {
									newElevation += tile.getElevation();
								}

								newElevation = Math.max(newElevation, 0);

								if (!NiftyUtils.isRelative("rotation")) {
									tile.setRotation((short) rotation);
								}

								if (!tile.getType().equals(newType)) {
									tile.setType(newType);
								}

								if (tile.getElevation() != newElevation) {
									tile.setElevation(newElevation);
								}
							}
						}
					}
				}
			}
		}
	}

}
