package com.mthwate.dominion.editor;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.GraphicalApp;
import com.mthwate.dominion.common.KeyControl;
import com.mthwate.dominion.common.MaterialUtils;
import com.mthwate.dominion.common.SaveUtils;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.mesh.Hexagon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mthwate
 */
public class EditorApp extends GraphicalApp {
	
	private File saveFile = new File("map.dwm");
	
	@Override
	public void init() {
		
		NiftyUtils.init(assetManager, inputManager, audioRenderer, guiViewPort);
		
		tryLoad();
		
		initLight();
		
		mapUpdate();
	}
	
	private void tryLoad() {
		if (saveFile.exists()) {
			TileStore.set(SaveUtils.load(saveFile));
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
				
				mapUpdate();

				NiftyUtils.gotoScreen("edit");
			} else {
				NiftyUtils.gotoScreen("menu");
			}
		}
	}
	
	@Override
	public void simpleUpdate(float tpf) {

		zoom(tpf);
		move(tpf);
		look();
		menu();

		if (keyHandler.isPressed(KeyControl.TOGGLE_WIRE)) {
			keyHandler.onAction(KeyControl.TOGGLE_WIRE.getName(), false, 0);
			
			if (wireNode.getParent() == null) {
				rootNode.attachChild(wireNode);
			} else {
				wireNode.removeFromParent();
			}
		}


		if (keyHandler.isPressed(KeyControl.INCREASE_BRUSH)) {
			keyHandler.onAction(KeyControl.INCREASE_BRUSH.getName(), false, 0);
			NiftyUtils.setMenuInt("brushSize", NiftyUtils.getMenuInt("brushSize") + 1);
		}

		if (keyHandler.isPressed(KeyControl.DECREASE_BRUSH)) {
			keyHandler.onAction(KeyControl.DECREASE_BRUSH.getName(), false, 0);
			NiftyUtils.setMenuInt("brushSize", Math.max(NiftyUtils.getMenuInt("brushSize") - 1, 0));
		}

		highlightNode.detachAllChildren();

		highlight(keyHandler.isPressed(KeyControl.CLICK));
	}
	
	private void highlight(boolean clicked) {
		CollisionResult result = clickCollisions().getClosestCollision();
		if (result != null) {
			Geometry geom = result.getGeometry();
			if (geom.getMesh() instanceof Hexagon) {
				String name = geom.getName();
				String[] split = name.split(",");
				int x = Integer.parseInt(split[0]);
				int y = Integer.parseInt(split[1]);

				String type = NiftyUtils.getMenuStr("type");

				int elevation = NiftyUtils.getMenuInt("elevation");

				int size = NiftyUtils.getMenuInt("brushSize");


				List<Set2i> coords = new ArrayList<Set2i>();

				for (int ix = -size + 1; ix < size; ix++) {
					for (int iy = -size + 1; iy < size; iy++) {
						if (Math.abs(ix + iy) < size) {
							int px = x + ix;
							int py = CoordUtils.hexToCartesian(px, CoordUtils.cartesianToHex(x, y) + iy);
							if (validPoint(px, py)) {

								Geometry g = new Geometry("selected");
								g.setMesh(hex);
								g.setQueueBucket(RenderQueue.Bucket.Transparent);
								g.setMaterial(MaterialUtils.getHighlightMaterial(assetManager));

								Tile tile = TileStore.get(px, py);

								float elev = tile.getElevation();
								
								g.setLocalTranslation(CoordUtils.getPosCartesian(px, py).setZ(elev * 0.75f + 0.002f));
								highlightNode.attachChild(g);
								
								if (clicked) {
									
									String newType = type;
									int newElevation = elevation;

									if (type.equals("")) {
										newType = tile.getType();
									}

									if (NiftyUtils.isRelative("elevation")) {
										newElevation += tile.getElevation();
									}

									newElevation = Math.max(newElevation, 0);
									
									boolean change = false;
									
									if (!tile.getType().equals(newType)) {
										tile.setType(newType);
										change = true;
									}
									
									if (tile.getElevation() != newElevation) {
										TileStore.get(px, py).setElevation(newElevation);
										change = true;
									}

									if (change) {
										coords.add(new Set2i(px, py));
									}
								}
							}
						}
					}
				}


				if (clicked) {
					Node[] nodes = {tileNode, sideNode, wireNode};
					for (Node node : nodes) {
						List<Integer> ints = new ArrayList<Integer>();

						for (int i = 0; i < node.getChildren().size(); i++) {
							String childName = node.getChild(i).getName();
							if (childName.contains(",")) {
								String[] childSplit = childName.split(",");
								int cx = Integer.parseInt(childSplit[0]);
								int cy = Integer.parseInt(childSplit[1]);

								if (coords.contains(new Set2i(cx, cy))) {
									ints.add(i);
								}
							}
						}

						int c = 0;

						for (int i : ints) {
							node.detachChildAt(i - c++);
						}
					}

					for (Set2i coord : coords) {
						updateTile(coord.getX(), coord.getY(), false);
					}
				}

			}
		}
	}
	
	private CollisionResults clickCollisions() {
		CollisionResults results = new CollisionResults();
		Vector2f click2d = inputManager.getCursorPosition();
		Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
		Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
		Ray ray = new Ray(click3d, dir);
		tileNode.collideWith(ray, results);
		return results;
	}

	private boolean validPoint(int x, int y) {
		boolean valid = true;
		if (x >= TileStore.sizeX() || x < 0) {
			valid = false;
		}
		if (y >= TileStore.sizeY() || y < 0) {
			valid = false;
		}
		return valid;
	}

	@Override
	public void close() {
		SaveUtils.save(saveFile, TileStore.get());
	}
}
