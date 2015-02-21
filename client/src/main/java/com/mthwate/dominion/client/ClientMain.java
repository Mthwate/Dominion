package com.mthwate.dominion.client;

import com.mthwate.dominion.common.Starter;

/**
 * @author mthwate
 */
public class ClientMain {
	
	public static void main(String[] args) {
		String ip = "localhost";
		
		if (args.length > 0) {
			ip = args[0];
		}
		
		Starter.start(new ClientApp(ip), true, "Dominion");
	}
	
}
