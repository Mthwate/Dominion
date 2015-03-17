package com.mthwate.dominion.common.tile;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mthwate
 */
@com.jme3.network.serializing.Serializable
public class TileProperties implements java.io.Serializable {

	@Getter @Setter private String name = "null";
	
	@Getter @Setter private String[] textures = {"null"};

	@Getter @Setter private String[] sides = {"stoneSide"};

	@Getter @Setter private String type = "land";

}
