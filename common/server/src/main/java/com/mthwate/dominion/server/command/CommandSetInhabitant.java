package com.mthwate.dominion.server.command;

import com.jme3.network.HostedConnection;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.entity.Entity;
import com.mthwate.dominion.common.message.MapMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.server.ServerApp;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mthwate
 */
@Slf4j
public class CommandSetInhabitant implements Command {
	
	@Override
	public String getName() {
		return "set-inhabitant";
	}

	@Override
	public void run(ServerApp app, String params) {
		String[] split = params.split(" ");

		if (split.length == 4) {
			String sx = split[0];
			String sy = split[1];
			String name = split[2];
			String owner = split[3];

			int x = Integer.parseInt(sx);
			int y = Integer.parseInt(sy);
			
			TileStore.get(x, y).setInhabitant(new Entity(name, owner));

			for (HostedConnection connection : app.getServer().getConnections()) {
				MessageUtils.send(connection, new MapMessage(TileStore.getTiles()));
			}
		} else {
			log.error("Invalid number of parameters");
		}
	}
	
}
