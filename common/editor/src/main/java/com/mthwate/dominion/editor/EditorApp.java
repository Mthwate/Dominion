package com.mthwate.dominion.editor;

import com.mthwate.datlib.math.set.Set2i;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.save.SaveUtils;
import com.mthwate.dominion.common.save.WorldMap;
import com.mthwate.dominion.editor.action.DecreaseBrushAction;
import com.mthwate.dominion.editor.action.IncreaseBrushAction;
import com.mthwate.dominion.editor.action.LeftClickAction;
import com.mthwate.dominion.editor.action.MenuAction;
import com.mthwate.dominion.editor.state.SpawnAppState;
import com.mthwate.dominion.graphical.GraphicalApp;
import com.mthwate.dominion.graphical.action.ActionRegistry;
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

		ActionRegistry.register(new MenuAction());

		ActionRegistry.register(new LeftClickAction(stateManager, rootNode));

		ActionRegistry.register(new IncreaseBrushAction());
		ActionRegistry.register(new DecreaseBrushAction());

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
	public void close() {
		Set2i[] spawnsArray = new Set2i[SpawnStore.size()];
		SpawnStore.toArray(spawnsArray);
		SaveUtils.saveMap(saveFile, new WorldMap(TileStore.getTiles(), spawnsArray));
	}
}
