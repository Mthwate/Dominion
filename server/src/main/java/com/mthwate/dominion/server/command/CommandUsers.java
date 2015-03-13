package com.mthwate.dominion.server.command;

import com.jme3.asset.AssetManager;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import lombok.extern.java.Log;

import java.util.logging.Logger;

/**
 * @author mthwate
 */
@Log
public class CommandUsers implements Command {
	
	@Override
	public String getName() {
		return "users";
	}

	@Override
	public void run(Server server, AssetManager assetManager, String params) {
		for (HostedConnection connection : server.getConnections()) {
			log.info(connection.getId() + ": " + connection.getAttribute("username"));
		}
	}
	
}
