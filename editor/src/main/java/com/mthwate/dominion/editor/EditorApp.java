package com.mthwate.dominion.editor;

import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.save.SaveUtils;
import com.mthwate.dominion.common.save.WorldMap;
import com.mthwate.dominion.editor.state.BrushAppState;
import com.mthwate.dominion.editor.state.MenuAppState;
import com.mthwate.dominion.editor.state.SpawnAppState;
import com.mthwate.dominion.graphical.*;
import com.mthwate.dominion.graphical.node.NodeHandler;
import com.mthwate.dominion.graphical.node.NodeTypeCollide;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author mthwate
 */
public class EditorApp extends GraphicalApp {
	
	private File saveFile = new File("map.dwm");

	private ArrayList<Set2i> spawns = new ArrayList<>();

	@Override
	protected void init() {
		
		super.init();

		NiftyUtils.init(this);
		
		tryLoad();
		
		initLight();

		NodeHandler.init("collision", new NodeTypeCollide(), null);

		stateManager.attach(new MenuAppState());
		stateManager.attach(new BrushAppState(rootNode));
		stateManager.attach(new SpawnAppState(rootNode, spawns));
	}
	
	private void tryLoad() {
		if (saveFile.exists()) {
			WorldMap map = SaveUtils.loadMap(saveFile);
			TileStore.setTiles(map.getTiles());
			spawns = new ArrayList<>(Arrays.asList(map.getSpawns()));
		} else {
			TileStore.resize(1, 1);
		}

		NiftyUtils.setMenuInt("width", TileStore.sizeX());
		NiftyUtils.setMenuInt("height", TileStore.sizeY());
	}
	
	@Override
	public void simpleUpdate(float tpf) {

		if (keyHandler.isPressed(KeyControl.INCREASE_BRUSH)) {
			keyHandler.onAction(KeyControl.INCREASE_BRUSH.getName(), false, 0);
			NiftyUtils.setMenuInt("brushSize", NiftyUtils.getMenuInt("brushSize") + 1);
		}

		if (keyHandler.isPressed(KeyControl.DECREASE_BRUSH)) {
			keyHandler.onAction(KeyControl.DECREASE_BRUSH.getName(), false, 0);
			NiftyUtils.setMenuInt("brushSize", Math.max(NiftyUtils.getMenuInt("brushSize") - 1, 0));
		}
	}

	public void toggleSpawn(Set2i spawn) {
		if (!spawns.remove(spawn)) {
			spawns.add(spawn);
		}
	}

	@Override
	public void close() {
		Set2i[] spawnsArray = new Set2i[spawns.size()];
		spawns.toArray(spawnsArray);
		SaveUtils.saveMap(saveFile, new WorldMap(TileStore.getTiles(), spawnsArray));
	}
}
