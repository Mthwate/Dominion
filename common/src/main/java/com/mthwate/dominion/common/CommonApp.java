package com.mthwate.dominion.common;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.asset.plugins.FileLocator;
import com.mthwate.dominion.common.entity.EproLoader;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mthwate
 */
public abstract class CommonApp extends SimpleApplication {

	private static final Logger log = Logger.getLogger(CommonApp.class.getName());
	
	protected abstract void init();
	
	protected abstract void close();

	@Override
	public void simpleInitApp() {
		log.info("Registering asset locator");
		
		if (new File("assets").exists()) {
			assetManager.unregisterLocator("/", ClasspathLocator.class);
			assetManager.registerLocator("assets", FileLocator.class);
			assetManager.registerLocator("/", ClasspathLocator.class);
		}

		assetManager.registerLoader(EproLoader.class, "epro");
		assetManager.registerLoader(ListLoader.class, "list");

		this.init();
	}
	
	@Override
	public void destroy() {
		this.close();

		super.destroy();
	}

	@Override
	public void handleError(String msg, Throwable err) {
		log.log(Level.SEVERE, msg, err);
	}
}
