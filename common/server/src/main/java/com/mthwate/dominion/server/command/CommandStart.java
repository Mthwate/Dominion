package com.mthwate.dominion.server.command;

import com.jme3.network.HostedConnection;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.TileStore;
import com.mthwate.dominion.common.entity.Entity;
import com.mthwate.dominion.common.message.MapMessage;
import com.mthwate.dominion.common.message.MessageUtils;
import com.mthwate.dominion.common.save.SaveUtils;
import com.mthwate.dominion.common.save.WorldMap;
import com.mthwate.dominion.server.ConnectionUtils;
import com.mthwate.dominion.server.ServerApp;
import lombok.extern.java.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

/**
 * @author mthwate
 */
@Log
public class CommandStart implements Command {

	@Deprecated
	private final File SAVE_FILE = new File("map.dwm");//TODO remove this

	@Override
	public String getName() {
		return "start";
	}

	@Override
	public void run(ServerApp app, String params) {


		WorldMap map = SaveUtils.loadMap(SAVE_FILE);

		TileStore.setTiles(map.getTiles());

		ArrayList<Set2i> spawns = new ArrayList<>(Arrays.asList(map.getSpawns()));

		Collection<HostedConnection> connections = app.getServer().getConnections();

		Random rand = new Random();

		if (spawns.size() >= connections.size()) {
			log.info("Starting the game with " + connections.size() + " connections");
			for (HostedConnection connection : connections) {
				int i = rand.nextInt(spawns.size());
				Set2i spawn = spawns.get(i);
				spawns.remove(i);
				Entity entity = new Entity("camp", ConnectionUtils.getUsername(connection));
				entity.store(new Entity("placeholder", ConnectionUtils.getUsername(connection)));
				TileStore.get(spawn).setInhabitant(entity);
				log.info(ConnectionUtils.getUsername(connection) + " is starting the game at " + spawn);
			}
		}

		MessageUtils.broadcast(app.getServer(), new MapMessage(TileStore.getTiles()));
	}

}
