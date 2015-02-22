package com.mthwate.dominion.server.command;

import com.jme3.asset.AssetManager;
import com.jme3.network.Server;

import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class CommandEcho implements Command {

	private static final Logger log = Logger.getLogger(CommandEcho.class.getName());
	
	public String getName() {
		return "echo";
	}
	
	public void run(Server server, AssetManager assetManager, String params) {
		log.info(params);
	}
	
}
