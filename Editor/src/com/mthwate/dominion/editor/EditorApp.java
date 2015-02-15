package com.mthwate.dominion.editor;

import com.jme3.asset.AssetNotFoundException;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.shadow.EdgeFilteringMode;
import com.mthwate.datlib.Vector2i;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.Log;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.editor.mesh.HexLine;
import com.mthwate.dominion.editor.mesh.HexSides;
import com.mthwate.dominion.editor.mesh.Hexagon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mthwate
 */
public class EditorApp extends CommonApp {
	
	private KeyHandler keyHandler;
	
	private Tile[][] tiles = new Tile[1][1];

	private Hexagon hex = new Hexagon(1);

	private HexLine hexLine = new HexLine(1);
	
	private File saveFile = new File("map.dwm");

	private Node highlightNode = new Node();

	private Node tileNode = new Node();

	private Node sideNode = new Node();

	private Node wireNode = new Node();
	
	@Override
	public void init() {

		if (new File("assets").exists()) {
			assetManager.registerLocator("assets", FileLocator.class);
		}

		assetManager.registerLoader(TproLoader.class, "tpro");
		
		rootNode.attachChild(highlightNode);

		rootNode.attachChild(tileNode);

		rootNode.attachChild(sideNode);

		rootNode.attachChild(wireNode);
		
		cam.setLocation(new Vector3f(0, -10, 15));
		cam.lookAtDirection(new Vector3f(0, 0.5f, -1), new Vector3f(0, 0, 1));
		
		keyHandler = new KeyHandler(inputManager);

		NiftyUtils.init(assetManager, inputManager, audioRenderer, guiViewPort);

		tryLoad();
		
		initLight();
		
		mapUpdate();
	}
	
	private void tryLoad() {
		if (saveFile.exists()) {
			tiles = SaveUtils.load(saveFile);
		}

		NiftyUtils.setMenuInt("width", tiles.length);
		NiftyUtils.setMenuInt("height", tiles[0].length);
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
	
	private void updateTile(int x, int y, boolean detach) {
		
		String name = x + "," + y;
		
		if (detach) {
			while (tileNode.detachChildNamed(name) != -1) {}
			while (sideNode.detachChildNamed(name) != -1) {}
			while (wireNode.detachChildNamed(name) != -1) {}
		}
			
		Tile tile = tiles[x][y];

		String type = tile.getType();
		float elevation = tile.getElevation() * 0.75F;

		Material mat;
		try {
			mat = TproUtils.getMaterialFace(type, assetManager);
		} catch (AssetNotFoundException e) {
			mat = MaterialUtils.getTexturedMaterial("null", assetManager);
		}

		Geometry geom = new Geometry(name);
		geom.setMesh(hex);
		geom.setMaterial(mat);
		geom.setLocalTranslation(CoordUtils.getPosCartesian(x, y).setZ(elevation));

		tileNode.attachChild(geom);



		Geometry wire = new Geometry(name);
		wire.setMesh(hexLine);
		wire.setMaterial(MaterialUtils.getWireMaterial(assetManager));
		wire.setLocalTranslation(CoordUtils.getPosCartesian(x, y).setZ(elevation + 0.002f));

		wireNode.attachChild(wire);


		if (elevation > 0) {
			Material matSides;
			
			try {
				matSides = TproUtils.getMaterialSide(type, assetManager);
			} catch (AssetNotFoundException e) {
				matSides = MaterialUtils.getTexturedMaterial("null", assetManager);
			}

			HexSides hexSides = new HexSides(1, elevation);

			Geometry geomSides = new Geometry(name);
			geomSides.setMesh(hexSides);
			geomSides.setMaterial(matSides);
			geomSides.setLocalTranslation(CoordUtils.getPosCartesian(x, y));

			sideNode.attachChild(geomSides);
		}
	}

	private void mapUpdate() {
		tileNode.detachAllChildren();
		sideNode.detachAllChildren();
		wireNode.detachAllChildren();
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
				updateTile(x, y, false);
			}
		}
	}
	
	private void zoom(float tpf) {
		
		float zoomMod = 100f;
		
		int zoom = 0;
		
		if (keyHandler.isPressed(KeyControl.ZOOM_IN)) {
			zoom--;
			keyHandler.onAction(KeyControl.ZOOM_IN.getName(), false, 0);
		}
		if (keyHandler.isPressed(KeyControl.ZOOM_OUT)) {
			zoom++;
			keyHandler.onAction(KeyControl.ZOOM_OUT.getName(), false, 0);
		}
		
		Vector3f location = cam.getLocation();
		
		float z = location.getZ() + (zoom * tpf * zoomMod);

		z = Math.max(z, 5);
		z = Math.min(z, 250);

		location.addLocal(0, 0, z - location.getZ());

		cam.setLocation(location);
	}

	private void move(float tpf) {

		float moveMod = 2f;
		
		Vector3f direction = cam.getDirection().setZ(0);
		Vector3f left = cam.getLeft().setZ(0);

		for (int i = 0; i < 3; i++) {
			if (direction.get(i) != 0) {
				direction.set(i, direction.get(i) / Math.abs(direction.get(i)));
			}
		}
		
		Vector3f move = new Vector3f();

		if (keyHandler.isPressed(KeyControl.LEFT)) {
			move.addLocal(left);
		}
		if (keyHandler.isPressed(KeyControl.RIGHT)) {
			move.addLocal(left.negate());
		}
		if (keyHandler.isPressed(KeyControl.UP)) {
			move.addLocal(direction);
		}
		if (keyHandler.isPressed(KeyControl.DOWN)) {
			move.addLocal(direction.negate());
		}

		float z = cam.getLocation().getZ();
		
		cam.setLocation(cam.getLocation().add(move.mult(moveMod * tpf * (z-3))));
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
				
				Tile[][] newTiles = new Tile[x][y];
				
				for (int xi = 0; xi < x; xi++) {
					for (int yi = 0; yi < y; yi++) {
						if (xi < tiles.length && yi < tiles[0].length) {
							newTiles[xi][yi] = tiles[xi][yi];
						} else {
							newTiles[xi][yi] = new Tile("null", 0);
						}
					}
				}
				
				tiles = newTiles;
				
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


				List<Vector2i> coords = new ArrayList<Vector2i>();

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

								Tile tile = tiles[px][py];

								float elev = tile.getElevation();
								
								g.setLocalTranslation(CoordUtils.getPosCartesian(px, py).setZ(elev * 0.75f + 0.002f));
								highlightNode.attachChild(g);
								
								if (clicked) {
									
									String t = type;
									int e = elevation;

									if (type.equals("")) {
										t = tile.getType();
									}

									if (NiftyUtils.isRelative("elevation")) {
										e += tile.getElevation();
									}
									
									tiles[px][py] = new Tile(t, Math.max(e, 0));
								}
								
								coords.add(new Vector2i(px, py));
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

								if (coords.contains(new Vector2i(cx, cy))) {
									ints.add(i);
								}
							}
						}

						int c = 0;

						for (int i : ints) {
							node.detachChildAt(i - c++);
						}
					}

					for (Vector2i coord : coords) {
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
		if (x >= tiles.length || x < 0) {
			valid = false;
		}
		if (y >= tiles[0].length || y < 0) {
			valid = false;
		}
		return valid;
	}

	@Override
	public void close() {
		SaveUtils.save(saveFile, tiles);
	}
}
