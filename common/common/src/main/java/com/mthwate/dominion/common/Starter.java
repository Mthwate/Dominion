package com.mthwate.dominion.common;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.mthwate.dominion.common.log.LogUtils;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author mthwate
 */
@Slf4j
public class Starter {

	public static void start(CommonApp app, boolean graphical, String name) {

		LogUtils.init();

		if (graphical) {
			AppSettings settings = new AppSettings(false);

			settings.setSettingsDialogImage("icons/title.png");

			InputStream in = app.getClass().getResourceAsStream("/icons/icon.png");
			if (in != null) {
				try {
					BufferedImage[] icons = new BufferedImage[]{ImageIO.read(in)};
					settings.setIcons(icons);
				} catch (IOException e) {
					log.error("Failed to load application icons", e);
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
