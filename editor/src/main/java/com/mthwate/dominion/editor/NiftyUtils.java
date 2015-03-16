package com.mthwate.dominion.editor;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.mthwate.datlib.FileUtils;
import com.mthwate.datlib.IOUtils;
import com.mthwate.dominion.graphical.tpro.TproUtils;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.TextField;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mthwate
 */
public class NiftyUtils {

	private static Nifty nifty;
	
	public static void init(Application app) {
		AssetManager assetManager = app.getAssetManager();
		ViewPort guiViewPort = app.getGuiViewPort();

		NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, app.getInputManager(), app.getAudioRenderer(), guiViewPort);
		nifty = niftyDisplay.getNifty();

		nifty.fromXml("gui.xml", "edit");

		for (String name : nifty.getAllScreensName()) {
			nifty.getScreen(name).startScreen();
		}

		guiViewPort.addProcessor(niftyDisplay);


		addTileOption("");

		List<String> tiles = new ArrayList<>();

		try {
			tiles.addAll(IOUtils.listZipContents(IOUtils.getClassJar(EditorApp.class), "tiles"));
		} catch (IOException e) {}

		File dir = new File("assets");

		if (dir.exists()) {
			tiles.addAll(FileUtils.listRecursive(dir));
		}

		for (String path : tiles) {
			String ext = ".tpro";
			if (path.endsWith(ext)) {
				String tile = path.substring(path.lastIndexOf("/") + 1, path.length() - ext.length());
				TproUtils.load(tile, assetManager);
				addTileOption(tile);
			}
		}
	}
	
	public static boolean isOnScreen(String screen) {
		return nifty.getCurrentScreen().getScreenId().equals(screen);
	}
	
	public static void gotoScreen(String screen) {
		nifty.gotoScreen(screen);
	}
	
	private static TextField getMenuTextField(String name) {
		return nifty.getScreen("menu").findNiftyControl(name, TextField.class);
	}

	private static DropDown<String> getMenuDropDown(String name) {
		return nifty.getScreen("menu").findNiftyControl(name, DropDown.class);
	}

	private static CheckBox getMenuCheckBox(String name) {
		return nifty.getScreen("menu").findNiftyControl(name, CheckBox.class);
	}
	
	public static String getTileSelection() {
		return getMenuDropDown("type").getSelection();
	}
	
	public static void addTileOption(String tile) {
		getMenuDropDown("type").addItem(tile);
	}

	private static void setMenuStr(String field, String contents) {
		TextField textField = getMenuTextField(field);
		textField.setText(contents);
	}

	public static void setMenuInt(String field, int contents) {
		setMenuStr(field, Integer.toString(contents));
	}

	public static String getMenuStr(String name) {
		TextField field = getMenuTextField(name);
		return field.getRealText();
	}

	public static int getMenuInt(String name) {
		String str = getMenuStr(name).replace("~", "");
		if (str.replace("-", "").equals("")) {
			str = "0";
		}
		return Integer.parseInt(str);
	}
	
	public static boolean isRelative(String name) {
		String str = getMenuStr(name);
		return str.contains("~") || str.equals("");
	}

	public static boolean isSpawn() {
		return getMenuCheckBox("spawn").isChecked();
	}
	
}
