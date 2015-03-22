package com.mthwate.dominion.graphical.mesh;

import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

/**
 * @author mthwate
 */
public class HexLine extends Mesh {
	
	public HexLine(float radius, float height) {

		float x = radius / 2f;
		float y = (float) Math.sqrt(3) * radius / 2f;

		Vector3f[] vertices = new Vector3f[18];
		vertices[0] = new Vector3f(-radius, 0, 0);
		vertices[1] = new Vector3f(-radius, 0, -height);
		vertices[2] = new Vector3f(-radius, 0, 0);

		vertices[3] = new Vector3f(-x, -y, 0);
		vertices[4] = new Vector3f(-x, -y, -height);
		vertices[5] = new Vector3f(-x, -y, 0);
		
		vertices[6] = new Vector3f(x, -y, 0);
		vertices[7] = new Vector3f(x, -y, -height);
		vertices[8] = new Vector3f(x, -y, 0);

		vertices[9] = new Vector3f(radius, 0, 0);
		vertices[10] = new Vector3f(radius, 0, -height);
		vertices[11] = new Vector3f(radius, 0, 0);

		vertices[12] = new Vector3f(x, y, 0);
		vertices[13] = new Vector3f(x, y, -height);
		vertices[14] = new Vector3f(x, y, 0);

		vertices[15] = new Vector3f(-x, y, 0);
		vertices[16] = new Vector3f(-x, y, -height);
		vertices[17] = new Vector3f(-x, y, 0);

		short[] indexes = new short[vertices.length * 2];
		
		for (int i = 0; i < indexes.length; i++) {
			indexes[i] = (short) ((i + 1) / 2);
			if (indexes[i] * 2 == indexes.length) {
				indexes[i] = 0;
			}
		}
		
		
		this.setMode(Mode.Lines);
		this.setLineWidth(2);
		
		this.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		this.setBuffer(VertexBuffer.Type.Index, 2, BufferUtils.createShortBuffer(indexes));
		this.updateBound();
		
	}
	
}
