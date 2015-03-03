package com.mthwate.dominion.editor;

import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.mthwate.dominion.graphical.tpro.TproUtils;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.TextField;

import java.util.List;

/**
 * @author mthwate
 */
public class NiftyUtils {

	private static Nifty nifty;
	
	public static void init(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort) {
		NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
		nifty = niftyDisplay.getNifty();

		nifty.fromXml("gui.xml", "edit");

		for (String name : nifty.getAllScreensName()) {
			nifty.getScreen(name).startScreen();
		}

		guiViewPort.addProcessor(niftyDisplay);


		addTileOption("");

		AssetKey<List<String>> key = new AssetKey<List<String>>("tiles/tiles.list");
		List<String> tiles = assetManager.loadAsset(key);

		for (String tile : tiles) {
			TproUtils.load(tile, assetManager);
			addTileOption(tile);
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
