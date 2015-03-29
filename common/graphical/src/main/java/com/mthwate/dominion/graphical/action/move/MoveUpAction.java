package com.mthwate.dominion.graphical.action.move;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.action.ActionKey;

/**
 * @author mthwate
 */
public class MoveUpAction extends MoveAction {

	public MoveUpAction(Camera camera) {
		super(camera);
	}

	@Override
	protected void onAction(float time) {
		Vector3f direction = cam.getDirection().setZ(0).normalize();

		float z = cam.getLocation().getZ();

		float mult = time * MOVE_SPEED * (z-2);

		Vector3f move = direction.mult(mult);

		cam.setLocation(cam.getLocation().add(move));
	}

	@Override
	public ActionKey getKey() {
		return ActionKey.UP;
	}

}
