package com.mthwate.dominion.common.entity;

import com.jme3.network.serializing.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mthwate
 */
@Serializable
public class EntityProperties {

	@Getter @Setter private String model = "null";

	@Getter @Setter private String texture = "null";

	@Getter @Setter private boolean moveable = false;

	@Getter @Setter private String[] travelType = {"land"};

	public EntityProperties() {}
	
}
