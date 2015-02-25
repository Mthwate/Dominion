package com.mthwate.dominion.graphical;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.graphical.mesh.HexSides;
import com.mthwate.dominion.graphical.mesh.Hexagon;
import com.mthwate.dominion.graphical.tpro.TproLoader;
import com.mthwate.dominion.graphical.tpro.TproUtils;

import java.util.logging.Logger;

/**
 * @author mthwate
 */
public abstract class GraphicalApp extends CommonApp {

	private static final Logger log = Logger.getLogger(GraphicalApp.class.getName());
	
	protected final Hexagon hex = new Hexagon(1);

	protected final Node highlightNode = new Node();

	protected KeyHandler keyHandler;

	@Override
	protected void init() {
		assetManager.registerLoader(TproLoader.class, "tpro");
		
		keyHandler = new KeyHandler(inputManager);


		log.info("Setting up nodes");
		rootNode.attachChild(highlightNode);
		NodeHandler.init(rootNode);
		

		log.info("Disabling the default fly camera");
		flyCam.setEnabled(false);


		log.info("Setting initial location");
		cam.setLocation(new Vector3f(0, -10, 15));


		log.info("Setting initial camera direction");
		cam.lookAtDirection(new Vector3f(0, 0.5f, -1), new Vector3f(0, 0, 1));
		
	}
	
	protected void mapUpdate() {
		NodeHandler.clear();
		for (int x = 0; x < TileStore.sizeX(); x++) {
			for (int y = 0; y < TileStore.sizeY(); y++) {
				updateTile(x, y, false);
			}
		}
	}

	protected void updateTile(int x, int y) {
		updateTile(x, y, true);
	}

	protected void updateTile(int x, int y, boolean detach) {
		
		if (detach) {
			NodeHandler.detach(x, y);
		}

		Tile tile = TileStore.get(x, y);

		String type = tile.getType();
		int elevation = tile.getElevation();

		float elevMod = 0.75f;
		
		Geometry tileGeom = addTile(type, x, y, elevation, elevMod);
		Geometry wireGeom = addWire(x, y, elevation, elevMod);
		Geometry sideGeom = addSides(type, x, y, elevation, elevMod);
		Spatial model = addInhabitant(tile, x, y, elevation, elevMod);
		NodeHandler.attach(tileGeom, sideGeom, wireGeom, model, x, y, elevation * elevMod);
	}
	
	private Geometry addTile(String type, int x, int y, int z, float elevMod) {
		Geometry geom = new Geometry();
		geom.setMesh(hex);
		geom.setMaterial(TproUtils.getMaterialFace(type, assetManager));
		return geom;
	}
	
	private Geometry addWire(int x, int y, int z, float elevMod) {
		Geometry wire = new Geometry();
		wire.setMesh(MeshUtils.getWire(z, elevMod));
		wire.setMaterial(MaterialUtils.getWireMaterial(assetManager));
		return wire;
	}
	
	private Geometry addSides(String type, int x, int y, int z, float elevMod) {
		Geometry geomSides = null;
		if (z > 0) {
			HexSides hexSides = MeshUtils.getSide(z, elevMod);
			geomSides = new Geometry();
			geomSides.setMesh(hexSides);
			geomSides.setMaterial(TproUtils.getMaterialSide(type, assetManager));
		}
		return geomSides;
	}
	
	private Spatial addInhabitant(Tile tile, int x, int y, float z, float elevMod) {
		Spatial model = null;
		if (tile.hasInhabitant()) {
			model = ModelUtils.getModel(tile.getInhabitant(), assetManager);
		}
		return model;
	}

	protected void zoom(float tpf) {

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

	protected void move(float tpf) {

		float moveMod = 2f;

		Vector3f direction = cam.getDirection().setZ(0);
		Vector3f left = cam.getLeft().setZ(0);

		for (int i = 0; i < 2; i++) {//loops through the x and y coordinates (z is already set to 0)
			if (direction.get(i) != 0) {
				direction.set(i, direction.get(i) / Math.abs(direction.get(i)));//sets the magnitude to 1 or -1
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
	
	protected void listenWire() {

		if (keyHandler.isPressed(KeyControl.TOGGLE_WIRE)) {
			keyHandler.onAction(KeyControl.TOGGLE_WIRE.getName(), false, 0);
			NodeHandler.toggleWire(rootNode);
		}

    }

	protected CollisionResults clickCollisions() {
		CollisionResults results = new CollisionResults();
		Vector2f click2d = inputManager.getCursorPosition();
		Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
		Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
		Ray ray = new Ray(click3d, dir);
		NodeHandler.collide(ray, results);
		return results;
	}
	
}
