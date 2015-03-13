package com.mthwate.dominion.server.messagehandler;

import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import com.mthwate.dominion.common.Path;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.MoveMessage;
import com.mthwate.dominion.server.ConnectionUtils;
import com.mthwate.dominion.server.PathHandler;

import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class MoveMessageHandler implements MessageHandler<MoveMessage> {

	private static final Logger log = Logger.getLogger(MoveMessageHandler.class.getName());

	@Override
	public void onMessage(Server server, HostedConnection connection, MoveMessage message) {
		Path path = message.getPath();

		if (path.isValid()) {
			Tile sourceTile = TileStore.get(path.getCurrent());

			if (sourceTile.hasInhabitant()) {
				if (sourceTile.getInhabitant().getOwner().equals(ConnectionUtils.getUsername(connection)) && sourceTile.getInhabitant().getType().moveable) {
					PathHandler.add(path);
				}
			}
		} else {
			log.warning("Invalid path received from connection " + connection.getId() + ", this my be the result of error or attempted cheating");
		}
	}

}
