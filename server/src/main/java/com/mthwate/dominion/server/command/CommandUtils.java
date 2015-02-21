package com.mthwate.dominion.server.command;

import com.jme3.asset.AssetManager;
import com.jme3.network.Server;
import com.mthwate.datlib.StringUtils;
import com.mthwate.dominion.common.log.Log;

/**
 * @author mthwate
 */
public class CommandUtils {
	
	public static void run(Server server, AssetManager assetManager, String line) {
		String[] split = line.split(" ");
		String name = split[0];
		Command cmd = CommandRegistry.getCommand(name);
		if (cmd != null) {
			cmd.run(server, assetManager, StringUtils.join(split, 1, " "));
		} else {
			Log.CONSOLE.info("Command not found: " + name);
		}
	}
	
}
