package com.mthwate.dominion.editor.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.mthwate.datlib.math.set.Set2i;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.tile.Tile;
import com.mthwate.dominion.common.tile.TileProperties;
import com.mthwate.dominion.common.tile.TproUtils;
import com.mthwate.dominion.editor.NiftyUtils;
import com.mthwate.dominion.editor.SpawnStore;
import com.mthwate.dominion.graphical.ClickUtils;
import com.mthwate.dominion.graphical.highlight.HighlightColors;
import com.mthwate.dominion.graphical.highlight.HighlightUtils;
import lombok.Setter;

/**
 * @author mthwate
 */
public class BrushAppState extends AbstractAppState {

	private Node node = new Node();

	private InputManager inputManager;

	private Camera cam;

	@Setter private boolean clicked = false;

	public BrushAppState(Node parentNode) {
		parentNode.attachChild(node);
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		inputManager = app.getInputManager();
		cam = app.getCamera();
	}

	@Override
	public void update(float tpf) {

		node.detachAllChildren();

		Set2i pos = ClickUtils.clickCollisionPos(inputManager, cam);

		if (pos != null) {
			int x = pos.getX();
			int y = pos.getY();

			if (NiftyUtils.isSpawn()) {
				HighlightUtils.highlightTile(x, y, HighlightColors.YELLOW, node);
				if (clicked) {
					clicked = false;
					SpawnStore.toggleSpawn(new Set2i(x, y));
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

									if (!tile.getType().equals(newType)) {
										tile.setType(newType.getName());
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

}
