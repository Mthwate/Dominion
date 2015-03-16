package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.KeyControl;

/**
 * @author mthwate
 */
public class ZoomAppState extends InputAppState {

	private Camera cam;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		cam = app.getCamera();
	}

	@Override
	public void update(float tpf) {
		float zoomMod = 100f;

		int zoom = 0;

		if (keyHandler.isPressed(KeyControl.ZOOM_IN)) {
			zoom--;
			keyHandler.unpress(KeyControl.ZOOM_IN);
		}
		if (keyHandler.isPressed(KeyControl.ZOOM_OUT)) {
			zoom++;
			keyHandler.unpress(KeyControl.ZOOM_OUT);
		}

		Vector3f location = cam.getLocation();

		float z = location.getZ() + (zoom * tpf * zoomMod);

		z = Math.max(z, 3);
		z = Math.min(z, 250);

		location.addLocal(0, 0, z - location.getZ());

		cam.setLocation(location);
	}

}
