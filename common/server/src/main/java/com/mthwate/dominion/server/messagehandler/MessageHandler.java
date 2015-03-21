package com.mthwate.dominion.server.messagehandler;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.Server;

/**
 * @author mthwate
 */
public interface MessageHandler<T extends Message> {

	public void onMessage(Server server, HostedConnection connection, T message);

}
