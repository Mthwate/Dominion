package com.mthwate.dominion.editor;

import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.save.SaveUtils;
import com.mthwate.dominion.common.save.WorldMap;
import com.mthwate.dominion.editor.state.MenuAppState;
import com.mthwate.dominion.editor.state.SpawnAppState;
import com.mthwate.dominion.graphical.*;
import com.mthwate.dominion.graphical.state.NodeAppState;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mthwate
 */
public class EditorApp extends GraphicalApp {
	
	private File saveFile = new File("map.dwm");

	private ArrayList<Set2i> spawns = new ArrayList<>();

	@Override
	protected void init() {
		
		super.init();

		NiftyUtils.init(this);
		
		tryLoad();
		
		initLight();

		stateManager.attach(new MenuAppState());
		stateManager.attach(new SpawnAppState(spawns));
	}
	
	private void tryLoad() {
		if (saveFile.exists()) {
			WorldMap map = SaveUtils.loadMap(saveFile);
			TileStore.setTiles(map.getTiles());
			spawns = new ArrayList<>(Arrays.asList(map.getSpawns()));
		} else {
			TileStore.resize(1, 1);
		}

		NiftyUtils.setMenuInt("width", TileStore.sizeX());
		NiftyUtils.setMenuInt("height", TileStore.sizeY());
	}
	
	@Override
	public void simpleUpdate(float tpf) {

		if (keyHandler.isPressed(KeyControl.INCREASE_BRUSH)) {
			keyHandler.onAction(KeyControl.INCREASE_BRUSH.getName(), false, 0);
			NiftyUtils.setMenuInt("brushSize", NiftyUtils.getMenuInt("brushSize") + 1);
		}

		if (keyHandler.isPressed(KeyControl.DECREASE_BRUSH)) {
			keyHandler.onAction(KeyControl.DECREASE_BRUSH.getName(), false, 0);
			NiftyUtils.setMenuInt("brushSize", Math.max(NiftyUtils.getMenuInt("brushSize") - 1, 0));
		}

		highlightNode.detachAllChildren();

		highlight();
	}

	public void addHighlight(int x, int y, ColorRGBA color) {
		Geometry g = new Geometry();
		g.setMesh(MeshUtils.getTile());
		g.setQueueBucket(RenderQueue.Bucket.Transparent);
		g.setMaterial(MaterialUtils.getHighlightMaterial(color, assetManager));

		Tile tile = TileStore.get(x, y);

		float elev = tile.getElevation();

		g.setLocalTranslation(CoordUtils.getPosCartesian(x, y).setZ(elev * 0.75f + 0.002f));
		highlightNode.attachChild(g);
	}

	private void toggleSpawn(Set2i spawn) {
		if (!spawns.remove(spawn)) {
			spawns.add(spawn);
		}
	}
	
	private void highlight() {
		Set2i pos = clickCollisionPos();
		if (pos != null) {
			int x = pos.getX();
			int y = pos.getY();

			boolean clicked = keyHandler.isPressed(KeyControl.CLICK);

			if (NiftyUtils.isSpawn()) {
				addHighlight(x, y, Highlighter.YELLOW);
				if (clicked) {
					keyHandler.onAction(KeyControl.CLICK.getName(), false, 0);
					toggleSpawn(new Set2i(x, y));
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

								addHighlight(px, py, Highlighter.YELLOW);

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

	@Override
	public void close() {
		Set2i[] spawnsArray = new Set2i[spawns.size()];
		spawns.toArray(spawnsArray);
		SaveUtils.saveMap(saveFile, new WorldMap(TileStore.getTiles(), spawnsArray));
	}
}
