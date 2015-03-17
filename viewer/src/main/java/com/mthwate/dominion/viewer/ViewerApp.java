package com.mthwate.dominion.viewer;

import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.mthwate.datlib.FileUtils;
import com.mthwate.datlib.IOUtils;
import com.mthwate.dominion.common.CoordUtils;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.entity.Entity;
import com.mthwate.dominion.common.entity.EproUtils;
import com.mthwate.dominion.graphical.GraphicalApp;
import com.mthwate.dominion.graphical.node.NodeHandler;
import com.mthwate.dominion.graphical.node.NodeTypeModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mthwate
 */
public class ViewerApp extends GraphicalApp {

	@Override
	protected void init() {

		super.init();

		initLight();

		NodeHandler.init("model", new NodeTypeModel(), rootNode);



		List<String> entityPaths = new ArrayList<>();

		try {
			entityPaths.addAll(IOUtils.listZipContents(IOUtils.getClassJar(ViewerApp.class), "entities"));
		} catch (IOException e) {}

		File dir = new File("assets/entities");

		if (dir.exists()) {
			entityPaths.addAll(FileUtils.listRecursive(dir));
		}

		List<String> entityNames = new ArrayList<>();

		for (String path : entityPaths) {
			String ext = ".epro";
			if (path.endsWith(ext)) {
				String entity = path.substring(path.lastIndexOf("/") + 1, path.length() - ext.length());
				entityNames.add(entity);
			}
		}



		int width = entityNames.size() * 2 + 1;
		int height = 3;

		TileStore.resize(width, height);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				TileStore.get(x, y).setType("grass");
				if (y == 1 && x % 2 == 1) {
					Tile tile = TileStore.get(x, y);
					tile.setElevation(1);
					String name = entityNames.get(x / 2);
					tile.setInhabitant(new Entity(EproUtils.getProperties(name, assetManager), null));

					BitmapText ch = new BitmapText(guiFont);
					ch.setSize(0.2F);
					ch.setText(name);
					ch.setColor(ColorRGBA.White);
					ch.setLocalTranslation(CoordUtils.getPosCartesian(x, 0).add(ch.getLineWidth() / -2, ch.getLineHeight() / 2, 0.001F));
					rootNode.attachChild(ch);
				}
			}
		}
	}

	@Override
	protected void close() {}
}