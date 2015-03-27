package com.mthwate.dominion.server.command;

import com.mthwate.datlib.StringUtils;
import com.mthwate.dominion.server.ServerApp;
import lombok.extern.java.Log;

/**
 * @author mthwate
 */
@Log
public class CommandUtils {
	
	public static void run(ServerApp app, String line) {
		String[] split = line.split(" ");
		String name = split[0];
		Command cmd = CommandRegistry.getCommand(name);
		if (cmd != null) {
			cmd.run(app, StringUtils.join(split, 1, " "));
		} else {
			log.info("Command not found: " + name);
		}
	}
	
}
