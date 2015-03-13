package com.mthwate.dominion.server.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.mthwate.dominion.server.command.CommandUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class ConsoleAppState extends ServerAppState {

	private static final Logger log = Logger.getLogger(ConsoleAppState.class.getName());

	private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	private AssetManager assetManager;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		assetManager = app.getAssetManager();
	}

	@Override
	public void update(float tpf) {
		try {
			while (stdin.ready()) {
				CommandUtils.run(server, assetManager, stdin.readLine());
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, "Failed to read from stdin", e);
		}
	}

}