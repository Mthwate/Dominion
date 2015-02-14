package com.mthwate.dominion.server;

import com.jme3.system.JmeContext;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class ServerMain {
	
	public static void main(String[] args) {
		Logger.getLogger("").setLevel(Level.OFF);
		ServerApp app = new ServerApp();
		app.start(JmeContext.Type.Headless);
	}
	
}
