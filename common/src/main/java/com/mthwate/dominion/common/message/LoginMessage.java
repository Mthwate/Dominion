package com.mthwate.dominion.common.message;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.jme3.network.serializing.Serializer;

/**
 * @author mthwate
 */
@Serializable
public class LoginMessage extends AbstractMessage {
	
	private String user;
	
	public LoginMessage() {}
	
	public LoginMessage(String username) {
		this.user = username;
	}
	
	public String getUsername() {
		return user;
	}
	
}
