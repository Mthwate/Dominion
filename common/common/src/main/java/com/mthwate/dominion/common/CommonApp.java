package com.mthwate.dominion.common;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.asset.plugins.FileLocator;
import com.mthwate.dominion.common.entity.EproLoader;
import com.mthwate.dominion.common.entity.EproUtils;
import com.mthwate.dominion.common.tile.TproUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author mthwate
 */
@Slf4j
public abstract class CommonApp extends SimpleApplication {
	
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

		TproUtils.init(assetManager);
		EproUtils.init(assetManager);

		this.init();
	}
	
	@Override
	public void destroy() {
		this.close();

		super.destroy();
	}

	@Override
	public void handleError(String msg, Throwable err) {
		log.error(msg, err);
	}
}
