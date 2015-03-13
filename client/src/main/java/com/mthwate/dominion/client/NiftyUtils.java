package com.mthwate.dominion.client;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * @author mthwate
 */
public class NiftyUtils implements ScreenController {

	private static Nifty nifty;

	private static boolean join = false;

	public static void init(Application app) {
		AssetManager assetManager = app.getAssetManager();
		ViewPort guiViewPort = app.getGuiViewPort();

		NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, app.getInputManager(), app.getAudioRenderer(), guiViewPort);
		nifty = niftyDisplay.getNifty();

		nifty.fromXml("gui.xml", "menu");

		for (String name : nifty.getAllScreensName()) {
			nifty.getScreen(name).startScreen();
		}

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

	public static String getMenuStr(String name) {
		TextField field = getMenuTextField(name);
		return field.getRealText();
	}

	public static boolean canJoin() {
		return join;
	}

	public static void setJoin(boolean canJoin) {
		join = canJoin;
	}

	public void joinServer() {
		join = true;
	}

	@Override
	public void bind(Nifty nifty, Screen screen) {}

	@Override
	public void onStartScreen() {}

	@Override
	public void onEndScreen() {}
}
