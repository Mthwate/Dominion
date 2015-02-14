package com.mthwate.dominion.editor;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

import java.lang.management.MemoryNotificationInfo;

/**
 * @author mthwate
 */
public class HexLine extends Mesh {
	
	public HexLine(float radius) {

		float x = radius / 2f;
		float y = (float) Math.sqrt(3) * radius / 2f;

		Vector3f[] vertices = new Vector3f[6];
		vertices[0] = new Vector3f(-radius, 0, 0);
		vertices[1] = new Vector3f(-x, -y, 0);
		vertices[2] = new Vector3f(x, -y, 0);
		vertices[3] = new Vector3f(radius, 0, 0);
		vertices[4] = new Vector3f(x, y, 0);
		vertices[5] = new Vector3f(-x, y, 0);

		int[] indexes = {0,1, 1,2, 2,3, 3,4, 4,5, 5,0};
		
		this.setMode(Mode.Lines);
		
		this.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		this.setBuffer(VertexBuffer.Type.Index, 2, BufferUtils.createIntBuffer(indexes));
		this.updateBound();
		
	}
	
}
