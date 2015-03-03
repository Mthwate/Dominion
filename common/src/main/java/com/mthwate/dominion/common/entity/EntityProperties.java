package com.mthwate.dominion.common.entity;

import com.jme3.network.serializing.Serializable;

/**
 * @author mthwate
 */
@Serializable
public class EntityProperties {

	public String model = "null";

	public String texture = "null";

	public boolean moveable = false;

	public EntityProperties() {}
	
}
