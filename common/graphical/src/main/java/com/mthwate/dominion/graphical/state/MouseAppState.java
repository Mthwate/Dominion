package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.graphical.ClickUtils;
import com.mthwate.dominion.graphical.KeyControl;
import com.mthwate.dominion.graphical.KeyHandler;

/**
 * @author mthwate
 */
public abstract class MouseAppState extends GraphicalAppState {

	protected KeyHandler keyHandler;

	protected InputManager inputManager;

	private Camera cam;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		keyHandler = gapp.getKeyHandler();
		inputManager = gapp.getInputManager();
		cam = gapp.getCamera();
	}

	@Override
	public void update(float tpf) {
		Set2i pos = ClickUtils.clickCollisionPos(inputManager, cam);
		if (pos != null) {
			boolean clickedL = keyHandler.isPressed(KeyControl.LEFT_CLICK);
			boolean clickedR = keyHandler.isPressed(KeyControl.LEFT_CLICK);
			update(tpf, pos, clickedL, clickedR);
		}
	}

	protected abstract void update(float tpf, Set2i pos, boolean clickedL, boolean clickedR);
}
