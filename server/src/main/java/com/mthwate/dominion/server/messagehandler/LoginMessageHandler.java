package com.mthwate.dominion.server.messagehandler;

import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.message.LoginMessage;
import com.mthwate.dominion.common.message.MapMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.server.ConnectionUtils;

/**
 * @author mthwate
 */
public class LoginMessageHandler implements MessageHandler<LoginMessage> {

	@Override
	public void onMessage(Server server, HostedConnection connection, LoginMessage message) {
		ConnectionUtils.setUsername(connection, message.getUsername());
		if (TileStore.getTiles() != null) {
			MessageUtils.send(connection, new MapMessage(TileStore.getTiles()));
		}
	}

}
