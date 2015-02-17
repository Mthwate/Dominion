package com.mthwate.dominion.common.mesh;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

/**
 * @author mthwate
 */
public class Hexagon extends Mesh {

	/**
	 * Creates a 2D hexagon mesh with the supplied radius.
	 *
	 * @param radius the radius of the hexagon
	 */
	public Hexagon(float radius) {

		float x = radius / 2f;
		float y = (float) Math.sqrt(3) * radius / 2f;

		Vector3f [] vertices = new Vector3f[7];
		vertices[0] = new Vector3f(0, 0, 0);
		vertices[1] = new Vector3f(-radius, 0, 0);
		vertices[2] = new Vector3f(-x, -y, 0);
		vertices[3] = new Vector3f(x, -y, 0);
		vertices[4] = new Vector3f(radius, 0, 0);
		vertices[5] = new Vector3f(x, y, 0);
		vertices[6] = new Vector3f(-x, y, 0);


		int[] indexes = {
				0,1,2, 2,3,0, 0,3,4, 4,5,0, 0,5,6, 6,1,0, // top
				//0,1,6, 6,5,0, 0,5,4, 4,3,0, 0,3,2, 2,1,0 // bottom
		};

		float[] normals = {
				0, 0, 1,
				0, 0, 1,
				0, 0, 1,
				0, 0, 1,
				0, 0, 1,
				0, 0, 1,
				0, 0, 1,
		};

		Vector2f[] texCoord = new Vector2f[7];
		texCoord[0] = new Vector2f(0.5f, 0.5f);
		texCoord[1] = new Vector2f(0, 0.5f);
		texCoord[2] = new Vector2f(0.25f, 0);
		texCoord[3] = new Vector2f(0.75f, 0);
		texCoord[4] = new Vector2f(1, 0.5f);
		texCoord[5] = new Vector2f(0.75f, 1);
		texCoord[6] = new Vector2f(0.25f, 1);

		this.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		this.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
		this.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indexes));
		this.setBuffer(VertexBuffer.Type.Normal, 3, BufferUtils.createFloatBuffer(normals));
		this.updateBound();
	}

}
