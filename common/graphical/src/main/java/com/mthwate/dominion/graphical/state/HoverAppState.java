package com.mthwate.dominion.graphical.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.entity.Entity;
import com.mthwate.dominion.graphical.ClickUtils;

/**
 * @author mthwate
 */
public class HoverAppState extends AbstractAppState {

	private Node node = new Node();

	private InputManager inputManager;

	private Camera cam;

	private Set2i prev;

	private float time = 0;

	private int height;

	private BitmapFont font;

	public HoverAppState(Node parentNode, AppSettings settings) {
		parentNode.attachChild(node);
		height = settings.getHeight();
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		inputManager = app.getInputManager();
		cam = app.getCamera();
		font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
	}

	@Override
	public void update(float tpf) {
		node.detachAllChildren();
		Set2i pos = ClickUtils.clickCollisionPos(inputManager, cam);
		if (pos != null && prev != null && prev.equals(pos)) {
			time += tpf;

			if (time > 1) {
				Entity inhabitant = TileStore.get(pos).getInhabitant();
				if (inhabitant != null) {
					float y = 0;
					for (Entity entity : inhabitant.getStorage()) {
						BitmapText hudText = new BitmapText(font, false);
						hudText.setSize(font.getCharSet().getRenderedSize());
						hudText.setColor(ColorRGBA.White);
						hudText.setText(entity.getName());
						hudText.setLocalTranslation(0, height - y, 0);
						y += hudText.getLineHeight();
						node.attachChild(hudText);
					}
				}
			}

		} else {
			time = 0;
		}
		prev = pos;
	}

}
