package com.mthwate.dominion.server.command;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class CommandRegistry {
	
	private static Map<String, Command> commands = new HashMap<String, Command>();
	
	static {
		init();
	}
	
	public static void init() {
		add(new CommandEcho());
		add(new CommandSetTile());
		add(new CommandUsers());
		add(new CommandSetInhabitant());
		add(new CommandStart());
	}
	
	private static void add(Command cmd) {
		commands.put(cmd.getName(), cmd);
	}

	protected static Command getCommand(String name) {
		return commands.get(name);
	}
	
}
