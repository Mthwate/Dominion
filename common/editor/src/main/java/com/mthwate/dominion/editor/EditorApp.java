package com.mthwate.dominion.editor;

import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.save.SaveUtils;
import com.mthwate.dominion.common.save.WorldMap;
import com.mthwate.dominion.editor.state.BrushAppState;
import com.mthwate.dominion.editor.state.MenuAppState;
import com.mthwate.dominion.editor.state.SpawnAppState;
import com.mthwate.dominion.graphical.GraphicalApp;
import com.mthwate.dominion.graphical.KeyControl;
import com.mthwate.dominion.graphical.node.NodeHandler;
import com.mthwate.dominion.graphical.node.NodeTypeCollide;

import java.io.File;

/**
 * @author mthwate
 */
public class EditorApp extends GraphicalApp {
	
	private File saveFile = new File("map.dwm");

	@Override
	protected void init() {
		
		super.init();

		NiftyUtils.init(this);
		
		tryLoad();
		
		initLight();

		NodeHandler.init("collision", new NodeTypeCollide(), null);

		stateManager.attach(new MenuAppState());
		stateManager.attach(new BrushAppState(rootNode));
		stateManager.attach(new SpawnAppState(rootNode));
	}
	
	private void tryLoad() {
		if (saveFile.exists()) {
			WorldMap map = SaveUtils.loadMap(saveFile);
			TileStore.setTiles(map.getTiles());
			SpawnStore.clear();
			for (Set2i spawn : map.getSpawns()) {
				SpawnStore.add(spawn);
			}
		} else {
			TileStore.resize(1, 1);
		}

		NiftyUtils.setMenuInt("width", TileStore.sizeX());
		NiftyUtils.setMenuInt("height", TileStore.sizeY());
	}
	
	@Override
	public void simpleUpdate(float tpf) {

		if (keyHandler.isPressed(KeyControl.INCREASE_BRUSH)) {
			keyHandler.unpress(KeyControl.INCREASE_BRUSH);
			NiftyUtils.setMenuInt("brushSize", NiftyUtils.getMenuInt("brushSize") + 1);
		}

		if (keyHandler.isPressed(KeyControl.DECREASE_BRUSH)) {
			keyHandler.unpress(KeyControl.DECREASE_BRUSH);
			NiftyUtils.setMenuInt("brushSize", Math.max(NiftyUtils.getMenuInt("brushSize") - 1, 0));
		}
	}

	@Override
	public void close() {
		Set2i[] spawnsArray = new Set2i[SpawnStore.size()];
		SpawnStore.toArray(spawnsArray);
		SaveUtils.saveMap(saveFile, new WorldMap(TileStore.getTiles(), spawnsArray));
	}
}
