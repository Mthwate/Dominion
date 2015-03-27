package com.mthwate.dominion.common.entity;

import com.jme3.network.serializing.Serializable;
import lombok.Getter;
import lombok.extern.java.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mthwate
 */
@Log
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
		return EproUtils.getProperties(type);
	}

	public void store(Entity entity) {
		log.info("Attempting to store " + entity.type + " in " + entity.type);
		if (storage.size() < getType().getStorage()) {
			storage.add(entity);
		} else {
			throw new IllegalArgumentException("Storage full");
		}
	}
}
