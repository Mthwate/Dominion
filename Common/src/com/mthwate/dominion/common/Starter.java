package com.mthwate.dominion.common;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class Starter {
	
	public static void start(CommonApp app, boolean graphical) {
		//Logger.getLogger("").setLevel(Level.OFF);

		if (graphical) {
			AppSettings settings = new AppSettings(false);

			settings.setSettingsDialogImage("textures/title.png");

			app.setSettings(settings);
			
			app.start();
		} else {
			app.start(JmeContext.Type.Headless);
		}
		
	}
	
}
