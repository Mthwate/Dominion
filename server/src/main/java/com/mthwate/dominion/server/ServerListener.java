package com.mthwate.dominion.server;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.dominion.common.Path;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.LoginMessage;
import com.mthwate.dominion.common.message.MapMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.common.message.MoveMessage;

import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class ServerListener implements MessageListener<HostedConnection> {

	private static final Logger log = Logger.getLogger(ServerListener.class.getName());
	
	private Server server;
	
	public ServerListener(Server server) {
		this.server = server;
	}
	
	@Override
	public void messageReceived(HostedConnection connection, Message m) {
		
		if (m instanceof GZIPCompressedMessage) {
			GZIPCompressedMessage zipped = (GZIPCompressedMessage) m;
			Message msg = zipped.getMessage();
			
			if (msg instanceof LoginMessage) {
				LoginMessage loginMsg = (LoginMessage) msg;
				ConnectionUtils.setUsername(connection, loginMsg.getUsername());
			} else if (msg instanceof MoveMessage) {
				MoveMessage moveMsg = (MoveMessage) msg;

				Path path = moveMsg.getPath();

				if (path.isValid()) {
					Tile sourceTile = TileStore.get(path.getCurrent());
					Tile targetTile = TileStore.get(path.getNext());

					if (sourceTile.hasInhabitant() && !targetTile.hasInhabitant()) {
						if (sourceTile.getInhabitant().getOwner().equals(ConnectionUtils.getUsername(connection)) && sourceTile.getInhabitant().getType().moveable) {
							targetTile.setInhabitant(sourceTile.getInhabitant());
							sourceTile.setInhabitant(null);
							MessageUtils.broadcast(server, new MapMessage(TileStore.get()));
						}
					}
				} else {
					log.warning("Invalid path received from connection " + connection.getId() + ", this my be the result of error or attempted cheating");
				}
			} else {
				log.warning("Invalid message type \"" + m.getClass() + "\" received");
			}
			
		} else {
			log.warning("Invalid message type \"" + m.getClass() + "\" received");
		}
		
	}
	
}
