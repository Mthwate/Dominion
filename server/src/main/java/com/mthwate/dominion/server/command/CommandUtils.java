package com.mthwate.dominion.server.command;

import com.mthwate.datlib.StringUtils;

/**
 * @author mthwate
 */
public class CommandUtils {
	
	public void run(String line) {
		String[] split = line.split(" ");
		String name = split[0];
		Command cmd = CommandRegistry.getCommand(name);
		cmd.run(line.substring(name.length()));
	}
	
}
