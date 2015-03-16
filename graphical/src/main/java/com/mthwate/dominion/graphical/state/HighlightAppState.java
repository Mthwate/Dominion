package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.graphical.ClickUtils;
import com.mthwate.dominion.graphical.KeyControl;
import com.mthwate.dominion.graphical.KeyHandler;
import com.mthwate.dominion.graphical.highlight.Highlighter;

/**
 * @author mthwate
 */
public class HighlightAppState extends GraphicalAppState {

	private KeyHandler keyHandler;

	private InputManager inputManager;

	private Camera cam;

	private Highlighter highlighter;

	public HighlightAppState(Highlighter highlighter) {
		this.highlighter = highlighter;
	}

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
			boolean clicked = keyHandler.isPressed(KeyControl.CLICK);
			highlighter.highlight(pos, clicked);
		}
	}

}
