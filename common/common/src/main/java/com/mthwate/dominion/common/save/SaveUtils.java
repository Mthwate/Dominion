package com.mthwate.dominion.common.save;

import com.mthwate.datlib.IOUtils;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.tile.Tile;
import lombok.extern.java.Log;

import java.io.*;
import java.util.logging.Level;

/**
 * @author mthwate
 */
@Log
public class SaveUtils {

	public static WorldMap loadMap(File file) {
		Tile tile = new Tile();
		Tile[][] tiles = {{tile}};
		WorldMap map = new WorldMap(tiles, new Set2i[0]);
		
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			map = (WorldMap) ois.readObject();
		} catch (Exception e) {
			log.log(Level.WARNING, "Error loading world map", e);
		} finally {
			IOUtils.close(fis);
			IOUtils.close(ois);
		}
		
		return map;
	}

	public static boolean saveMap(File file, WorldMap map) {
		boolean sucess = false;
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(map);
			sucess = true;
		} catch (Exception e) {
			log.log(Level.WARNING, "Error saving world map", e);
		} finally {
			IOUtils.close(fos);
			IOUtils.close(oos);
		}
		
		return sucess;
	}

	@Deprecated
	public static Tile[][] load(File saveFile) {
		return loadMap(saveFile).getTiles();
	}

	@Deprecated
	public static void save(File saveFile, Tile[][] tiles) {
		saveMap(saveFile, new WorldMap(tiles, null));
	}
}
