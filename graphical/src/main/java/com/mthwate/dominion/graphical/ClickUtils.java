package com.mthwate.dominion.graphical;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.graphical.node.NodeHandler;

/**
 * @author mthwate
 */
public class ClickUtils {

	public static Set2i clickCollisionPos(InputManager inputManager, Camera cam) {
		Set2i pos = null;
		CollisionResult result = clickCollisions(inputManager, cam).getClosestCollision();
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

	private static CollisionResults clickCollisions(InputManager inputManager, Camera cam) {
		CollisionResults results = new CollisionResults();
		Vector2f click2d = inputManager.getCursorPosition();
		Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
		Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
		Ray ray = new Ray(click3d, dir);
		NodeHandler.collide(ray, results);
		return results;
	}

}
