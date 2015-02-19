package com.mthwate.dominion.server.command;

import com.mthwate.dominion.common.log.Log;

/**
 * @author mthwate
 */
public class CommandEcho implements Command {
	
	public String getName() {
		return "echo";
	}
	
	public void run(String params) {
		Log.TMP.info(params);
	}
	
}
