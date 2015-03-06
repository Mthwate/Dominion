package com.mthwate.dominion.common.message;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.mthwate.datlib.math.Set2i;
import com.mthwate.dominion.common.Path;

/**
 * @author mthwate
 */
@Serializable
public class MoveMessage extends AbstractMessage {

	private Path path;

	@Deprecated
	public MoveMessage() {}

	public MoveMessage(Path path) {
		this.path = path;
	}

	public Path getPath() {
		return this.path;
	}
	
}
