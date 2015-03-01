package com.mthwate.dominion.common;

import com.mthwate.datlib.IOUtils;

import java.io.*;

/**
 * @author mthwate
 */
public class SaveUtils {

	public static Tile[][] load(File file) {
		Tile[][] tiles = new Tile[1][1];
		
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			tiles = (Tile[][]) ois.readObject();
		} catch (Exception e) {} finally {
			IOUtils.close(fis);
			IOUtils.close(ois);
		}
		
		return tiles;
	}

	public static boolean save(File file, Tile[][] tiles) {
		boolean sucess = false;
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(tiles);
			sucess = true;
		} catch (Exception e) {} finally {
			IOUtils.close(fos);
			IOUtils.close(oos);
		}
		
		return sucess;
	}
	
}
