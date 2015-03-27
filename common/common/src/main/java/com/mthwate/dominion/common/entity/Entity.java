package com.mthwate.dominion.common.entity;

import com.jme3.network.serializing.Serializable;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mthwate
 */
@Serializable
public class Entity {

	private String type;

	@Getter private String owner;

	private Set<Entity> storage = new HashSet<>();

	@Deprecated
	public Entity() {}

	public Entity(String type, String owner) {
		this.type = type;
		this.owner = owner;
	}

	public EntityProperties getType() {
		return EproUtils.getProperties("");
	}

	public void store(Entity entity) {
		if (storage.size() < getType().getStorage()) {
			storage.add(entity);
		} else {
			throw new IllegalArgumentException("Storage full");
		}
	}
}
