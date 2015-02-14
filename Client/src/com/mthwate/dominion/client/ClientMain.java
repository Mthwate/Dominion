package com.mthwate.dominion.client;

import com.jme3.system.AppSettings;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class ClientMain {
	
	public static void main(String[] args) {
		Logger.getLogger("").setLevel(Level.OFF);

		AppSettings settings = new AppSettings(false);
		
		settings.setSettingsDialogImage("textures/title.png");
		
		ClientApp app = new ClientApp();
		app.setSettings(settings);
		app.start();
	}
	
}
