package com.mthwate.dominion.common.entity;

import com.jme3.network.serializing.Serializable;

/**
 * @author mthwate
 */
@Serializable
public class Entity {

	EntityProperties type;

	String owner;

	@Deprecated
	public Entity() {}

	public Entity(EntityProperties type, String owner) {
		this.type = type;
		this.owner = owner;
	}

	public EntityProperties getType() {
		return this.type;
	}

	public String getOwner() {
		return this.owner;
	}
}
