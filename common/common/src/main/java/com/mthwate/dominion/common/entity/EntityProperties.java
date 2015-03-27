package com.mthwate.dominion.common.entity;

import com.jme3.network.serializing.Serializable;
import lombok.Getter;

/**
 * @author mthwate
 */
@Serializable
public class EntityProperties {

	@Getter private String model = "null";

	@Getter private String texture = "null";

	@Getter private boolean moveable = false;

	@Getter private String[] travelType = {"land"};

	@Getter private int storage = 0;

	public EntityProperties() {}
	
}
