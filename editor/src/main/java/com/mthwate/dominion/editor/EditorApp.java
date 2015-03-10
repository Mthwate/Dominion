package com.mthwate.dominion.editor;

import com.jme3.collision.CollisionResult;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.save.SaveUtils;
import com.mthwate.dominion.common.save.WorldMap;
import com.mthwate.dominion.graphical.*;
import com.mthwate.dominion.graphical.mesh.Hexagon;
import com.mthwate.dominion.graphical.node.NodeHandler;
import com.mthwate.dominion.graphical.state.HomeAppState;
import com.mthwate.dominion.graphical.state.MoveAppState;
import com.mthwate.dominion.graphical.state.WireAppState;
import com.mthwate.dominion.graphical.state.ZoomAppState;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mthwate
 */
public class EditorApp extends GraphicalApp {
	
	private File saveFile = new File("map.dwm");

	private ArrayList<Set2i> spawns = new ArrayList<Set2i>();

	@Override
	protected void init() {
		
		super.init();

		NiftyUtils.init(assetManager, inputManager, audioRenderer, guiViewPort);
		
		tryLoad();
		
		initLight();

		stateManager.attach(new MoveAppState());
		stateManager.attach(new ZoomAppState());
		stateManager.attach(new HomeAppState());
		stateManager.attach(new WireAppState());
	}
	
	private void tryLoad() {
		if (saveFile.exists()) {
			WorldMap map = SaveUtils.loadMap(saveFile);
			TileStore.set(map.getTiles());
			spawns = new ArrayList<>(Arrays.asList(map.getSpawns()));
		} else {
			TileStore.resize(1, 1);
		}

		NiftyUtils.setMenuInt("width", TileStore.sizeX());
		NiftyUtils.setMenuInt("height", TileStore.sizeY());
	}
	
	private void initLight() {
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1));
		rootNode.addLight(al);
		
		DirectionalLight dl = new DirectionalLight();
		dl.setDirection(new Vector3f(1, 0, -1));
		dl.setColor(ColorRGBA.White.mult(1));
		rootNode.addLight(dl);
	}
	
	private void look() {
		float x = 0;
		float y = 0;

		if (keyHandler.isPressed(KeyControl.NORTH)) {
			y += 0.5;
		}

		if (keyHandler.isPressed(KeyControl.SOUTH)) {
			y -= 0.5;
		}

		if (keyHandler.isPressed(KeyControl.EAST)) {
			x += 0.5;
		}

		if (keyHandler.isPressed(KeyControl.WEST)) {
			x -= 0.5;
		}

		if ((x != 0 || y != 0) && Math.abs(x) != Math.abs(y)) {
			cam.lookAtDirection(new Vector3f(x, y, -1), new Vector3f(0, 0, 1));
		}
	}
	
	private void menu() {
		
		if (keyHandler.isPressed(KeyControl.MENU)) {
			keyHandler.onAction(KeyControl.MENU.getName(), false, 0);
			if (NiftyUtils.isOnScreen("menu")) {

				int x = NiftyUtils.getMenuInt("width");

				int y = NiftyUtils.getMenuInt("height");
				
				TileStore.resize(x, y);

				NiftyUtils.gotoScreen("edit");
			} else {
				NiftyUtils.gotoScreen("menu");
			}
		}
	}
	
	@Override
	public void simpleUpdate(float tpf) {

		look();
		menu();

		screenshot();

		if (keyHandler.isPressed(KeyControl.INCREASE_BRUSH)) {
			keyHandler.onAction(KeyControl.INCREASE_BRUSH.getName(), false, 0);
			NiftyUtils.setMenuInt("brushSize", NiftyUtils.getMenuInt("brushSize") + 1);
		}

		if (keyHandler.isPressed(KeyControl.DECREASE_BRUSH)) {
			keyHandler.onAction(KeyControl.DECREASE_BRUSH.getName(), false, 0);
			NiftyUtils.setMenuInt("brushSize", Math.max(NiftyUtils.getMenuInt("brushSize") - 1, 0));
		}

		highlightNode.detachAllChildren();

		checkSpawns();
		renderSpawns();

		highlight();
		
		NodeHandler.update(assetManager);
	}

	private void renderSpawns() {
		if (NiftyUtils.isSpawn()) {
			for (Set2i spawn : spawns) {
				addHighlight(spawn.getX(), spawn.getY(), Highlighter.BLUE);
			}
		}
	}

	private void addHighlight(int x, int y, ColorRGBA color) {
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

	private void checkSpawns() {
		List<Set2i> remove = new ArrayList<Set2i>();
		for (Set2i spawn : spawns) {
			if (!TileStore.validPoint(spawn)) {
				remove.add(spawn);
			}
		}
		spawns.removeAll(remove);
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
		SaveUtils.saveMap(saveFile, new WorldMap(TileStore.get(), spawnsArray));
	}
}
