package com.mthwate.dominion.graphical.action.zoom;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.action.AnalogAction;

/**
 * @author mthwate
 */
public abstract class ZoomAction extends AnalogAction {

	protected final float ZOOM_SPEED = 3f;

	protected final int ZOOM_MAX = 250;

	protected final int ZOOM_MIN = 3;

	protected Camera cam;

	public ZoomAction(Camera camera) {
		cam = camera;
	}

	@Override
	protected void onAction(float time) {
		Vector3f location = cam.getLocation();

		float z = location.getZ() + (time * ZOOM_SPEED * getMult());

		z = Math.max(z, ZOOM_MIN);
		z = Math.min(z, ZOOM_MAX);

		location.addLocal(0, 0, z - location.getZ());

		cam.setLocation(location);
	}

	protected abstract int getMult();

}
