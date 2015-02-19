package com.mthwate.dominion.server.command;

/**
 * @author mthwate
 */
public interface Command {

	public String getName();

	public void run(String params);
	
}
