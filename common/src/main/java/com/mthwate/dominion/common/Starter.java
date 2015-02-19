package com.mthwate.dominion.common;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.mthwate.dominion.common.CommonApp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class Starter {
	
	public static void start(CommonApp app, boolean graphical, String name) {
		Logger.getLogger("").setLevel(Level.OFF);

		if (graphical) {
			AppSettings settings = new AppSettings(false);

			settings.setSettingsDialogImage("textures/title.png");

			InputStream in = app.getClass().getResourceAsStream("/textures/icon.png");
			if (in != null) {
				try {
					BufferedImage[] icons = new BufferedImage[]{ImageIO.read(in)};
					settings.setIcons(icons);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			settings.setTitle(name);

			app.setSettings(settings);
			
			app.start();
		} else {
			app.start(JmeContext.Type.Headless);
		}
		
	}
	
}
