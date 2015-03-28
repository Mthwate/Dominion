package com.mthwate.dominion.server;

import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.server.state.ConsoleAppState;
import com.mthwate.dominion.server.state.PathAppState;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author mthwate
 */
@Slf4j
public class ServerApp extends CommonApp {

	/**
	 * The server networking object.
	 */
	@Getter private Server server;
	
	@Override
	protected void init() {

		try {
			server = Network.createServer(6969);//TODO modular port
		} catch (IOException e) {
			log.error("Failed to create server" ,e);
		}

		if (server != null) {
			MessageUtils.register();//Registers the network message types
			server.addMessageListener(new ServerListener(server), GZIPCompressedMessage.class);
			server.start();
		}


		stateManager.attach(new ConsoleAppState());
		stateManager.attach(new PathAppState());
	}

	@Override
	protected void close() {
		log.info("Stopping server");
		if (server != null) {
			server.close();
		}
	}
}
