package com.mthwate.dominion.graphical;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.graphical.node.NodeHandler;
import com.mthwate.dominion.graphical.tpro.TproLoader;
import lombok.extern.java.Log;

/**
 * @author mthwate
 */
@Log
public abstract class GraphicalApp extends CommonApp {

	protected final Node highlightNode = new Node();

	protected KeyHandler keyHandler;

	public static final Vector3f CAM_ORIGIN = new Vector3f(0, -10, 15);

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


		viewPort.setBackgroundColor(ColorRGBA.Black);
	}

	public KeyHandler getKeyHandler() {
		return keyHandler;
	}

	protected Set2i clickCollisionPos() {
		Set2i pos = null;
		CollisionResult result = clickCollisions().getClosestCollision();
		if (result != null) {
			Geometry geom = result.getGeometry();
			String name = geom.getName();
			String[] split = name.split(",");
			int x = Integer.parseInt(split[0]);
			int y = Integer.parseInt(split[1]);
			pos = new Set2i(x, y);
		}
		return pos;
	}

	private CollisionResults clickCollisions() {
		CollisionResults results = new CollisionResults();
		Vector2f click2d = inputManager.getCursorPosition();
		Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
		Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
		Ray ray = new Ray(click3d, dir);
		NodeHandler.collide(ray, results);
		return results;
	}
	
}
