package com.mthwate.dominion.server;

import com.mthwate.dominion.common.Starter;

/**
 * @author mthwate
 */
public class ServerMain {

	public static void main(String[] args) {
		Starter.start(new ServerApp(), false, null);
	}

}
