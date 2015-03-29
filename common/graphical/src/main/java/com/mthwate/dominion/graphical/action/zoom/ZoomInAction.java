package com.mthwate.dominion.graphical.action.zoom;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.action.ActionKey;

/**
 * @author mthwate
 */
public class ZoomInAction extends ZoomAction {

	public ZoomInAction(Camera camera) {
		super(camera);
	}

	@Override
	protected void onAction(float time) {
		Vector3f location = cam.getLocation();

		float z = location.getZ() - (time * ZOOM_SPEED);

		z = Math.max(z, ZOOM_MIN);
		z = Math.min(z, ZOOM_MAX);

		location.addLocal(0, 0, z - location.getZ());

		cam.setLocation(location);
	}

	@Override
	public ActionKey getKey() {
		return ActionKey.ZOOM_IN;
	}

}
