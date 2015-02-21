package com.mthwate.dominion.graphical;

import com.jme3.asset.AssetNotFoundException;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.epro.EntityProperties;
import com.mthwate.dominion.common.epro.EproUtils;
import com.mthwate.dominion.common.log.Log;
import com.mthwate.dominion.graphical.mesh.HexLine;
import com.mthwate.dominion.graphical.mesh.HexSides;
import com.mthwate.dominion.graphical.mesh.Hexagon;
import com.mthwate.dominion.graphical.tpro.TproLoader;
import com.mthwate.dominion.graphical.tpro.TproUtils;

/**
 * @author mthwate
 */
public abstract class GraphicalApp extends CommonApp {

	protected Hexagon hex = new Hexagon(1);

	//protected HexLine hexLine = new HexLine(1);
	
	protected Node highlightNode = new Node();

	protected Node tileNode = new Node();

	protected Node sideNode = new Node();

	protected Node wireNode = new Node();

	protected Node modelNode = new Node();

	protected KeyHandler keyHandler;

	@Override
	protected void init() {
		assetManager.registerLoader(TproLoader.class, "tpro");
		
		keyHandler = new KeyHandler(inputManager);
		
		rootNode.attachChild(highlightNode);

		rootNode.attachChild(tileNode);

		rootNode.attachChild(sideNode);

		rootNode.attachChild(wireNode);

		rootNode.attachChild(modelNode);
		

		Log.MAIN.info("Disabling the fly camera");

		flyCam.setEnabled(false);
		
		
		cam.setLocation(new Vector3f(0, -10, 15));
		cam.lookAtDirection(new Vector3f(0, 0.5f, -1), new Vector3f(0, 0, 1));
		
	}
	
	protected void mapUpdate() {
		tileNode.detachAllChildren();
		sideNode.detachAllChildren();
		wireNode.detachAllChildren();
		modelNode.detachAllChildren();
		for (int x = 0; x < TileStore.sizeX(); x++) {
			for (int y = 0; y < TileStore.sizeY(); y++) {
				updateTile(x, y, false);
			}
		}
	}
	
	protected void updateTile(int x, int y, boolean detach) {

		String name = x + "," + y;

		if (detach) {
			while (tileNode.detachChildNamed(name) != -1) {}
			while (sideNode.detachChildNamed(name) != -1) {}
			while (wireNode.detachChildNamed(name) != -1) {}
			while (modelNode.detachChildNamed(name) != -1) {}
		}

		Tile tile = TileStore.get(x, y);

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
		wire.setMesh(new HexLine(1, elevation));
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
		
		if (tile.hasInhabitant()) {
			EntityProperties inhabitant = tile.getInhabitant();
			
			Spatial model = assetManager.loadModel("obj/" + inhabitant.model + ".obj");
			model.setName(name);
			model.setMaterial(MaterialUtils.getTexturedMaterial(inhabitant.texture, assetManager));
			model.setLocalTranslation(CoordUtils.getPosCartesian(x, y).setZ(elevation + 0.004f));
			model.setLocalScale(0.1f, 0.1f, 0.1f);

			Quaternion rotation = new Quaternion();
			rotation.fromAngleAxis(FastMath.PI / 2, new Vector3f(1, 0, 0));
			model.setLocalRotation(rotation);

			model.setQueueBucket(RenderQueue.Bucket.Transparent);


			modelNode.attachChild(model);
		}
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

			if (wireNode.getParent() == null) {
				rootNode.attachChild(wireNode);
			} else {
				wireNode.removeFromParent();
			}
		}
		
	}

	protected CollisionResults clickCollisions() {
		CollisionResults results = new CollisionResults();
		Vector2f click2d = inputManager.getCursorPosition();
		Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
		Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
		Ray ray = new Ray(click3d, dir);
		tileNode.collideWith(ray, results);
		return results;
	}
	
}
