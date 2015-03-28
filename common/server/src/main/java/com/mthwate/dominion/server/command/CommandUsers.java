package com.mthwate.dominion.server.command;

import com.jme3.network.HostedConnection;
import com.mthwate.dominion.server.ServerApp;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mthwate
 */
@Slf4j
public class CommandUsers implements Command {
	
	@Override
	public String getName() {
		return "users";
	}

	@Override
	public void run(ServerApp app, String params) {
		for (HostedConnection connection : app.getServer().getConnections()) {
			log.info(connection.getId() + ": " + connection.getAttribute("username"));
		}
	}
	
}
