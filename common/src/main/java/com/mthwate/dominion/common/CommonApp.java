package com.mthwate.dominion.common;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.mthwate.dominion.common.log.Log;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author mthwate
 */
public abstract class CommonApp extends SimpleApplication {

	protected abstract void init();
	
	protected abstract void close();

	@Override
	public void simpleInitApp() {
		Log.MAIN.info("Initializing log");
		

		Log.MAIN.info("Registering asset locator");
		
		if (new File("assets").exists()) {
			assetManager.registerLocator("assets", FileLocator.class);
		}
		

		this.init();
	}
	
	@Override
	public void destroy() {
		this.close();

		Log.MAIN.info("Closing log");
		Log.close();

		super.destroy();
	}

	@Override
	public void handleError(String msg, Throwable err) {
		Log.MAIN.error(msg);

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		err.printStackTrace(printWriter);

		Log.MAIN.info(stringWriter.toString());
	}
}
