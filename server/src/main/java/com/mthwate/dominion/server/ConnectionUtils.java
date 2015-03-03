package com.mthwate.dominion.server;

import com.jme3.network.HostedConnection;

/**
 * @author mthwate
 */
public class ConnectionUtils {

	private static final String KEY_USERNAME = "username";

	public static String getUsername(HostedConnection connection) {
		return connection.getAttribute(KEY_USERNAME);
	}

	public static void setUsername(HostedConnection connection, String username) {
		connection.setAttribute(KEY_USERNAME, username);
	}

}
