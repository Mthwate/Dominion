package com.mthwate.dominion.editor;

import com.jme3.asset.AssetNotFoundException;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.mthwate.datlib.Vector2i;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.common.Tile;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;

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
	
	private Nifty nifty;
	
	private boolean inMenu = false;
	
	private File saveFile = new File("map.dwm");

	@Override
	public void init() {

		if (new File("assets").exists()) {
			assetManager.registerLocator("assets", FileLocator.class);
		}
		
		flyCam.setEnabled(false);
		
		cam.setLocation(new Vector3f(0, 0, 15));
		cam.lookAt(new Vector3f(0, 10, 0), cam.getUp());

		keyHandler = new KeyHandler(inputManager);

		initHud();
		
		if (saveFile.exists()) {
			tiles = SaveUtils.load(saveFile);
		}

		setMenuInt("width", tiles.length);
		setMenuInt("height", tiles[0].length);

		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(4));
		rootNode.addLight(al);

		mapUpdate();
	}

	private static Vector3f getPosCartesian(int x, int y) {
		return getPosHex(x, cartesianToHex(x, y));
	}

	private static Vector3f getPosHex(int x, int y) {
		return new Vector3f(x * 3f / 2f, (float) ((Math.sqrt(3) * y) + (Math.sqrt(3) * x / 2)), 0);
	}
	
	private void updateTile(int x, int y, boolean detach) {
		
		String name = x + "," + y;
		
		if (detach) {
			while (rootNode.detachChildNamed(name) != -1) {}
		}
			
		Tile tile = tiles[x][y];

		String type = "null";
		float elevation = 0;

		if (tile != null) {
			type = tile.getType();
			elevation = tile.getElevation() * 0.75F;
		}

		Material mat;
		try {
			mat = MaterialUtils.getTexturedMaterial(type, assetManager);
		} catch (AssetNotFoundException e) {
			mat = MaterialUtils.getTexturedMaterial("null", assetManager);
		}

		Geometry geom = new Geometry(name);
		geom.setMesh(hex);
		geom.setMaterial(mat);
		geom.setLocalTranslation(getPosCartesian(x, y).setZ(elevation));
		geom.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

		rootNode.attachChild(geom);



		Geometry wire = new Geometry(name);
		wire.setMesh(hexLine);
		wire.setMaterial(MaterialUtils.getWireMaterial(assetManager));
		wire.setLocalTranslation(getPosCartesian(x, y).setZ(elevation + 0.002f));

		rootNode.attachChild(wire);


		if (elevation > 0) {
			Material matSides = MaterialUtils.getTexturedMaterial("stone", assetManager);

			try {
				matSides = MaterialUtils.getTexturedMaterial(type + "Side", assetManager);
			} catch (AssetNotFoundException e) {}
			
			HexSides hexSides = new HexSides(1, elevation);

			Geometry geomSides = new Geometry(name);
			geomSides.setMesh(hexSides);
			geomSides.setMaterial(matSides);
			geomSides.setLocalTranslation(getPosCartesian(x, y));
			geomSides.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

			rootNode.attachChild(geomSides);
		}
	}

	private void mapUpdate() {
		rootNode.detachAllChildren();
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
				updateTile(x, y, false);
			}
		}
	}
	
	private void initHud() {
		NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
		nifty = niftyDisplay.getNifty();
		try {
			nifty.validateXml("test.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		nifty.fromXml("test.xml", "edit");
		
		for (String name : nifty.getAllScreensName()) {
			nifty.getScreen(name).startScreen();
		}

		setMenuInt("elevation", 0);
		setMenuInt("brushSize", 1);

		guiViewPort.addProcessor(niftyDisplay);
	}
	
	private void move(float tpf) {
		
		float moveMod = 2f;
		float zoomMod = 100f;

		int ud = 0;
		int rl = 0;
		int zoom = 0;
		
		if (keyHandler.isPressed(KeyControl.LEFT)) {
			rl--;
		}
		if (keyHandler.isPressed(KeyControl.RIGHT)) {
			rl++;
		}
		if (keyHandler.isPressed(KeyControl.UP)) {
			ud++;
		}
		if (keyHandler.isPressed(KeyControl.DOWN)) {
			ud--;
		}
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

		location.addLocal(rl * moveMod * tpf * (z-3), ud * moveMod * tpf * (z-3), z - location.getZ());

		cam.setLocation(location);

	}
	
	private TextField getMenuTextField(String name) {
		return nifty.getScreen("menu").findNiftyControl(name, TextField.class);
	}

	private void setMenuStr(String field, String contents) {
		TextField textField = getMenuTextField(field);
		textField.setText(contents);
	}

	private void setMenuInt(String field, int contents) {
		setMenuStr(field, Integer.toString(contents));
	}
	
	private String getMenuStr(String name) {
		TextField field = getMenuTextField(name);
		return field.getRealText();
	}
	
	private int getMenuInt(String name) {
		return Integer.parseInt(getMenuStr(name));
	}
	
	private void menu() {
		
		if (keyHandler.isPressed(KeyControl.MENU)) {
			keyHandler.onAction(KeyControl.MENU.getName(), false, 0);
			if (inMenu) {

				int x = getMenuInt("width");

				int y = getMenuInt("height");
				
				Tile[][] newTiles = new Tile[x][y];
				
				for (int xi = 0; xi < x; xi++) {
					for (int yi = 0; yi < y; yi++) {
						if (xi < tiles.length && yi < tiles[0].length) {
							newTiles[xi][yi] = tiles[xi][yi];
						}
					}
				}
				
				tiles = newTiles;
				
				mapUpdate();
				
				nifty.gotoScreen("edit");
			} else {
				nifty.gotoScreen("menu");
			}
			inMenu = !inMenu;
		}
	}
	
	@Override
	public void simpleUpdate(float tpf) {
		
		move(tpf);
		menu();

		if (keyHandler.isPressed(KeyControl.INCREASE_BRUSH)) {
			keyHandler.onAction(KeyControl.INCREASE_BRUSH.getName(), false, 0);
			setMenuInt("brushSize", getMenuInt("brushSize") + 1);
		}

		if (keyHandler.isPressed(KeyControl.DECREASE_BRUSH)) {
			keyHandler.onAction(KeyControl.DECREASE_BRUSH.getName(), false, 0);

			int size = getMenuInt("brushSize") - 1;

			size = Math.max(size, 1);

			setMenuInt("brushSize", size);
		}
		
		if (keyHandler.isPressed(KeyControl.CLICK)) {
			//keyHandler.onAction(KeyControl.MOUSE_LEFT.getName(), false, 0);

			CollisionResults results = new CollisionResults();
			Vector2f click2d = inputManager.getCursorPosition();
			Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
			Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
			Ray ray = new Ray(click3d, dir);
			rootNode.collideWith(ray, results);

			CollisionResult result = results.getClosestCollision();
			if (result != null) {
				Geometry geom = result.getGeometry();
				if (geom.getMesh() instanceof Hexagon) {
					String name = geom.getName();
					String[] split = name.split(",");
					int x = Integer.parseInt(split[0]);
					int y = Integer.parseInt(split[1]);

					String type = getMenuStr("type");

					int elevation = getMenuInt("elevation");
					
					int size = getMenuInt("brushSize");


					List<Vector2i> coords = new ArrayList<Vector2i>();
					
					for (int ix = -size + 1; ix < size; ix++) {
						for (int iy = -size + 1; iy < size; iy++) {
							if (Math.abs(ix + iy) < size) {
								int px = x + ix;
								int py = hexToCartesian(px, cartesianToHex(x, y) + iy);
								if (validPoint(px, py)) {
									tiles[px][py] = new Tile(type, elevation);
									coords.add(new Vector2i(px, py));
									//updateTile(px, py, true);
								}
							}
						}
					}

					List<Integer> ints = new ArrayList<Integer>();
					
					for (int i = 0; i < rootNode.getChildren().size(); i++) {
						String childName = rootNode.getChild(i).getName();
						String[] childSplit = childName.split(",");
						int cx = Integer.parseInt(childSplit[0]);
						int cy = Integer.parseInt(childSplit[1]);
						
						if (coords.contains(new Vector2i(cx, cy))) {
							ints.add(i);
						}
						
					}
					
					int c = 0;
					
					for (int i : ints) {
						rootNode.detachChildAt(i - c
								++);
					}
					
					for (Vector2i coord : coords) {
						updateTile(coord.getX(), coord.getY(), false);
					}

					
				}
			}
			
		}
	}

	private static int cartesianToHex(int x, int y) {
		return y - (x / 2);
	}

	private static int hexToCartesian(int x, int y) {
		return y + (x / 2);
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
