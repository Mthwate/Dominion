package com.mthwate.dominion.graphical.action.move;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.action.AnalogAction;

/**
 * @author mthwate
 */
public abstract class MoveAction extends AnalogAction {

	protected float MOVE_SPEED = 0.5f;

	protected Camera cam;

	public MoveAction(Camera camera) {
		cam = camera;
	}

	@Override
	protected void onAction(float time) {
		Vector3f direction = getDirection().setZ(0).normalize();

		float z = cam.getLocation().getZ();

		float mult = time * MOVE_SPEED * (float) Math.pow(z, 1.2);

		Vector3f move = direction.mult(mult);

		cam.setLocation(cam.getLocation().add(move));
	}

	protected abstract Vector3f getDirection();

}
