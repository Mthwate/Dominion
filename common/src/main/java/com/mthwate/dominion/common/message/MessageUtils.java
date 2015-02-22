package com.mthwate.dominion.common.message;

import com.jme3.network.Message;
import com.jme3.network.MessageConnection;
import com.jme3.network.Server;
import com.jme3.network.message.GZIPCompressedMessage;
import com.jme3.network.serializing.Serializer;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.epro.EntityProperties;

/**
 * @author mthwate
 */
public class MessageUtils {

	public static void send(MessageConnection connection, Message message) {
		connection.send(new GZIPCompressedMessage(message));
	}

	public static void broadcast(Server server, Message message) {
		server.broadcast(new GZIPCompressedMessage(message));
	}
	
	public static void register() {
		Serializer.registerClass(LoginMessage.class);
		Serializer.registerClass(TileMessage.class);
		Serializer.registerClass(MapMessage.class);
		Serializer.registerClass(MoveMessage.class);
		
		Serializer.registerClass(Tile.class);
		Serializer.registerClass(EntityProperties.class);
	}
	
}
