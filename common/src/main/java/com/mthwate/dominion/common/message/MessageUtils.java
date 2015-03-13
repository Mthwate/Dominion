package com.mthwate.dominion.common.message;

import com.jme3.network.Message;
import com.jme3.network.MessageConnection;
import com.jme3.network.Server;
import com.jme3.network.message.GZIPCompressedMessage;
import com.jme3.network.serializing.Serializer;
import com.mthwate.dominion.common.Path;
import com.mthwate.dominion.common.SerializableSet2i;
import com.mthwate.dominion.common.Tile;
import com.mthwate.dominion.common.entity.Entity;
import com.mthwate.dominion.common.entity.EntityProperties;
import lombok.extern.java.Log;

import java.util.logging.Logger;

/**
 * @author mthwate
 */
@Log
public class MessageUtils {

	/**
	 * Compresses the message and sends it to the connection.
	 *
	 * @param connection the connection to send the message to
	 * @param message the message to compress and send
	 */
	public static void send(MessageConnection connection, Message message) {
		log.info("sending message of type " + message.getClass());
		connection.send(new GZIPCompressedMessage(message));
	}

	/**
	 * Compresses and broadcasts a message to all clients connected to the server.
	 *
	 * @param server the server object to broadcast from
	 * @param message the message to compress and send
	 */
	public static void broadcast(Server server, Message message) {
		log.info("broadcasting message of type " + message.getClass());
		server.broadcast(new GZIPCompressedMessage(message));
	}

	/**
	 * Registers the classes to be transferred over the network.
	 */
	public static void register() {
		Serializer.registerClass(LoginMessage.class);
		Serializer.registerClass(TileMessage.class);
		Serializer.registerClass(MapMessage.class);
		Serializer.registerClass(MoveMessage.class);

		Serializer.registerClass(Tile.class);
		Serializer.registerClass(SerializableSet2i.class);
		Serializer.registerClass(Path.class);
		Serializer.registerClass(Entity.class);
		Serializer.registerClass(EntityProperties.class);
	}
	
}
