package com.mthwate.dominion.editor;

import com.jme3.system.AppSettings;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class EditorMain {

	public static void main(String[] args) {
		Logger.getLogger("").setLevel(Level.OFF);
		
		AppSettings settings = new AppSettings(false);

		settings.setSettingsDialogImage("textures/MapEditorLogo.png");

		EditorApp app = new EditorApp();
		app.setSettings(settings);
		app.start();
	}

}
