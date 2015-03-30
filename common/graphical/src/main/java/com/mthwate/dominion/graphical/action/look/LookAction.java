package com.mthwate.dominion.graphical.action.look;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.GraphicalApp;
import com.mthwate.dominion.graphical.action.AnalogAction;

/**
 * @author mthwate
 */
public abstract class LookAction extends AnalogAction {

	protected Camera cam;

	public LookAction(Camera camera) {
		cam = camera;
	}

	@Override
	protected void onAction(float time) {
		final Vector3f dir = cam.getDirection();

		float angle = (float) Math.atan2(dir.getX(), dir.getY());

		angle += time * getMult();

		float x = (float) Math.sin(angle);
		float y = (float) Math.cos(angle);

		cam.lookAtDirection(new Vector3f(x, y, -1), GraphicalApp.UP);
	}

	protected abstract int getMult();

}
