package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mthwate.dominion.graphical.GraphicalApp;
import com.mthwate.dominion.graphical.KeyControl;
import com.mthwate.dominion.graphical.KeyHandler;

/**
 * @author mthwate
 */
public class LookAppState extends GraphicalAppState {

	private KeyHandler keyHandler;

	private Camera cam;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		keyHandler = gapp.getKeyHandler();
		cam = gapp.getCamera();
	}

	@Override
	public void update(float tpf) {

		final Vector3f dir = cam.getDirection();

		float angle = (float) Math.atan2(dir.getX(), dir.getY());

		if (keyHandler.isPressed(KeyControl.LOOK_RIGHT)) {
			angle += tpf;
		}

		if (keyHandler.isPressed(KeyControl.LOOK_LEFT)) {
			angle -= tpf;
		}

		float x = (float) Math.sin(angle);
		float y = (float) Math.cos(angle);

		cam.lookAtDirection(new Vector3f(x, y, -1), GraphicalApp.UP);

		if (keyHandler.isPressed(KeyControl.LOOK_NORTH)) {
			cam.lookAtDirection(new Vector3f(0, 1, -1), GraphicalApp.UP);
		}
	}

}
