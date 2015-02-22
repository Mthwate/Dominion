package com.mthwate.dominion.server.command;

import com.jme3.asset.AssetManager;
import com.jme3.network.Server;
import com.mthwate.datlib.StringUtils;

import java.util.logging.Logger;

/**
 * @author mthwate
 */
public class CommandUtils {

	private static final Logger log = Logger.getLogger(CommandUtils.class.getName());
	
	public static void run(Server server, AssetManager assetManager, String line) {
		String[] split = line.split(" ");
		String name = split[0];
		Command cmd = CommandRegistry.getCommand(name);
		if (cmd != null) {
			cmd.run(server, assetManager, StringUtils.join(split, 1, " "));
		} else {
			log.info("Command not found: " + name);
		}
	}
	
}
