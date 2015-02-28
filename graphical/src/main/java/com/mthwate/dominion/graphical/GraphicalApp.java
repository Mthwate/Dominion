package com.mthwate.dominion.graphical;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.graphical.node.NodeHandler;
import com.mthwate.dominion.graphical.tpro.TproLoader;

import java.util.logging.Logger;

/**
 * @author mthwate
 */
public abstract class GraphicalApp extends CommonApp {

	private static final Logger log = Logger.getLogger(GraphicalApp.class.getName());

	protected final Node highlightNode = new Node();

	protected KeyHandler keyHandler;

	protected static final Vector3f CAM_ORIGIN = new Vector3f(0, -10, 15);
	
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
		cam.setLocation(CAM_ORIGIN);


		log.info("Setting initial camera direction");
		cam.lookAtDirection(new Vector3f(0, 0.5f, -1), new Vector3f(0, 0, 1));
		
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

	/**
	 * Listens for the "home" key to return the camera to the home position.
	 */
	protected void listenHome() {

		if (keyHandler.isPressed(KeyControl.GOTO_HOME)) {
			keyHandler.onAction(KeyControl.GOTO_HOME.getName(), false, 0);
			cam.setLocation(CAM_ORIGIN.setZ(cam.getLocation().getZ()));
		}
		
	}

	/**
	 * Listens for the "toggle wire" key to toggle the wire frame.
	 */
	protected void listenWire() {
		
		if (keyHandler.isPressed(KeyControl.TOGGLE_WIRE)) {
			keyHandler.onAction(KeyControl.TOGGLE_WIRE.getName(), false, 0);
			NodeHandler.toggleWire();
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
