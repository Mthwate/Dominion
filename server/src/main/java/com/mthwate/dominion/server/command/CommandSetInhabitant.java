package com.mthwate.dominion.server.command;

import com.jme3.asset.AssetManager;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.entity.Entity;
import com.mthwate.dominion.common.entity.EproUtils;
import com.mthwate.dominion.common.message.MapMessage;
import com.mthwate.dominion.common.message.MessageUtils;

import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class CommandSetInhabitant implements Command {

	private static final Logger log = Logger.getLogger(CommandSetInhabitant.class.getName());
	
	@Override
	public String getName() {
		return "set-inhabitant";
	}

	@Override
	public void run(Server server, AssetManager assetManager, String params) {
		String[] split = params.split(" ");

		if (split.length == 3) {
			String sx = split[0];
			String sy = split[1];
			String name = split[2];

			int x = Integer.parseInt(sx);
			int y = Integer.parseInt(sy);
			
			TileStore.get(x, y).setInhabitant(new Entity(EproUtils.getProperties(name, assetManager), null));

			for (HostedConnection connection : server.getConnections()) {
				MessageUtils.send(connection, new MapMessage(TileStore.get()));
			}
		} else {
			log.severe("Error");
		}
	}
	
}
