package com.mthwate.dominion.common;

import com.jme3.network.serializing.Serializable;
import com.mthwate.datlib.math.Set2i;

/**
 * @author mthwate
 */
@Serializable
public class SerializableSet2i extends Set2i {

	public SerializableSet2i() {
		this(0, 0);
	}

	public SerializableSet2i(Set2i set) {
		super(set.getX(), set.getY());
	}

	public SerializableSet2i(int x, int y) {
		super(x, y);
	}

}
