package com.mthwate.dominion.editor;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;

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

		setMenuInt("elevation", 0);
		setMenuInt("brushSize", 1);

		guiViewPort.addProcessor(niftyDisplay);
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
		return Integer.parseInt(getMenuStr(name));
	}
	
}
