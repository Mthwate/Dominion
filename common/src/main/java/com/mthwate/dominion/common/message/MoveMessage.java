package com.mthwate.dominion.common.message;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.mthwate.datlib.math.Set2i;

/**
 * @author mthwate
 */
@Serializable
public class MoveMessage extends AbstractMessage {

	int sx;
	int sy;

	int tx;
	int ty;
	
	public MoveMessage() {}

	public MoveMessage(Set2i source, Set2i target) {
		this.sx = source.getX();
		this.sy = source.getY();
		this.tx = target.getX();
		this.ty = target.getY();
	}

	public Set2i getSource() {
		return new Set2i(sx, sy);
	}

	public Set2i getTarget() {
		return new Set2i(tx, ty);
	}
	
}
