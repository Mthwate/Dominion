package com.mthwate.dominion.server;

import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.message.GZIPCompressedMessage;
import com.mthwate.dominion.common.CommonApp;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.server.command.CommandUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class ServerApp extends CommonApp {

	private static final Logger log = Logger.getLogger(ServerApp.class.getName());

	/**
	 * The server networking object.
	 */
	private Server server;

	private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	protected void init() {

		try {
			server = Network.createServer(6969);
		} catch (IOException e) {
			e.printStackTrace();
			//TODO log this
		}

		if (server != null) {
			MessageUtils.register();//Registers the network message types
			server.addMessageListener(new ServerListener(server), GZIPCompressedMessage.class);
			server.start();
		}

	}

	@Override
	public void simpleUpdate(float tpf) {

		//CommandUtils.run(server, assetManager, reader.nextLine());


		try {
			while (stdin.ready()) {
				CommandUtils.run(server, assetManager, stdin.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		PathHandler.update(server, tpf);
	}

	@Override
	protected void close() {
		log.info("Stopping server");
		if (server != null) {
			server.close();
		}
	}
	
}
