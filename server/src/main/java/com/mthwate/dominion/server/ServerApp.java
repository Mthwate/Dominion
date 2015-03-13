package com.mthwate.dominion.server;

import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.server.state.ConsoleAppState;
import com.mthwate.dominion.server.state.PathAppState;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mthwate
 */
@Log
public class ServerApp extends CommonApp {

	/**
	 * The server networking object.
	 */
	private Server server;
	
	@Override
	protected void init() {

		try {
			server = Network.createServer(6969);//TODO modular port
		} catch (IOException e) {
			log.log(Level.SEVERE, "Failed to create server" ,e);
		}

		if (server != null) {
			MessageUtils.register();//Registers the network message types
			server.addMessageListener(new ServerListener(server), GZIPCompressedMessage.class);
			server.start();
		}


		stateManager.attach(new ConsoleAppState());
		stateManager.attach(new PathAppState());
	}

	public Server getServer() {
		return server;
	}

	@Override
	protected void close() {
		log.info("Stopping server");
		if (server != null) {
			server.close();
		}
	}
}
