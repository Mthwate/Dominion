package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.KeyControl;

/**
 * @author mthwate
 */
public class LookAppState extends InputAppState {

	private Camera cam;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		cam = app.getCamera();
	}

	@Override
	public void update(float tpf) {
		float x = 0;
		float y = 0;

		if (keyHandler.isPressed(KeyControl.NORTH)) {
			y += 0.5;
		}

		if (keyHandler.isPressed(KeyControl.SOUTH)) {
			y -= 0.5;
		}

		if (keyHandler.isPressed(KeyControl.EAST)) {
			x += 0.5;
		}

		if (keyHandler.isPressed(KeyControl.WEST)) {
			x -= 0.5;
		}

		if ((x != 0 || y != 0) && Math.abs(x) != Math.abs(y)) {
			cam.lookAtDirection(new Vector3f(x, y, -1), new Vector3f(0, 0, 1));
		}
	}

}
