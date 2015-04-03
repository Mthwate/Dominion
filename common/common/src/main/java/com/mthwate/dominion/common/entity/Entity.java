package com.mthwate.dominion.common.entity;

import com.jme3.network.serializing.Serializable;
import com.mthwate.dominion.common.Path;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mthwate
 */
@Slf4j
@Serializable
public class Entity {

	private String type;

	@Getter @Setter private transient Path path;

	@Getter private String owner;

	private Set<Entity> storage = new HashSet<>();

	@Deprecated
	public Entity() {}

	public Entity(String type, String owner) {
		this.type = type;
		this.owner = owner;
	}

	public String getName() {
		return type;
	}

	public EntityProperties getType() {
		return EproUtils.getProperties(type);
	}

	public void store(Entity entity) {
		log.info("Attempting to store {} in {}" + entity.type, type);
		if (storage.size() < getType().getStorage()) {
			storage.add(entity);
		} else {
			throw new IllegalArgumentException("Storage full");
		}
	}

	public Set<Entity> getStorage() {
		return new HashSet<>(storage);
	}
}
