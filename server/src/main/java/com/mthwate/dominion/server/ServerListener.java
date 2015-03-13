package com.mthwate.dominion.server;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.dominion.common.message.LoginMessage;
import com.mthwate.dominion.common.message.MoveMessage;
import com.mthwate.dominion.server.messagehandler.LoginMessageHandler;
import com.mthwate.dominion.server.messagehandler.MessageHandler;
import com.mthwate.dominion.server.messagehandler.MoveMessageHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class ServerListener implements MessageListener<HostedConnection> {

	private static final Logger log = Logger.getLogger(ServerListener.class.getName());
	
	private Server server;

	private Map<Class, MessageHandler> handlers = new HashMap<>();
	
	public ServerListener(Server server) {
		this.server = server;

		//TODO move these somewhere else
		handlers.put(MoveMessage.class, new MoveMessageHandler());
		handlers.put(LoginMessage.class, new LoginMessageHandler());
	}
	
	@Override
	public void messageReceived(HostedConnection connection, Message message) {
		
		if (message instanceof GZIPCompressedMessage) {

			GZIPCompressedMessage zipped = (GZIPCompressedMessage) message;

			Message unzipped = zipped.getMessage();

			Class<? extends Message> clazz = unzipped.getClass();

			handlers.get(clazz).onMessage(server, connection, unzipped);
			
		} else {

			log.warning("Invalid message type \"" + message.getClass() + "\" received");

		}
		
	}
	
}
