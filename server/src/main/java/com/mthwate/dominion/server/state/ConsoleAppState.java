package com.mthwate.dominion.server.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.mthwate.dominion.server.command.CommandUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author mthwate
 */
public class ConsoleAppState extends ServerAppState {

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
			e.printStackTrace();
		}
	}

}