package com.mthwate.dominion.common.mesh;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

/**
 * @author mthwate
 */
public class HexSides extends Mesh {
	
	public HexSides(float radius, float height) {

		float x = radius / 2f;
		float y = (float) Math.sqrt(3) * radius / 2f;

		Vector3f[] vertices = new Vector3f[12];
		vertices[0] = new Vector3f(-radius, 0, height);
		vertices[2] = new Vector3f(-x, -y, height);
		vertices[4] = new Vector3f(x, -y, height);
		vertices[6] = new Vector3f(radius, 0, height);
		vertices[8] = new Vector3f(x, y, height);
		vertices[10] = new Vector3f(-x, y, height);


		vertices[1] = new Vector3f(-radius, 0, 0);
		vertices[3] = new Vector3f(-x, -y, 0);
		vertices[5] = new Vector3f(x, -y, 0);
		vertices[7] = new Vector3f(radius, 0, 0);
		vertices[9] = new Vector3f(x, y, 0);
		vertices[11] = new Vector3f(-x, y, 0);
		
		Vector3f[] normals = new Vector3f[vertices.length];
		
		float half = height / 2;
		
		for (int i = 0; i < vertices.length; i++) {
			normals[i] = vertices[i].clone();
			normals[i].setZ(half * vertices[i].getZ() == 0 ? -1 : 1);
		}


		int[] indexes = {
				0,1,2, 3,2,1, 2,3,4, 5,4,3, 4,5,6, 7,6,5,
				6,7,8, 9,8,7, 8,9,10, 11,10,9, 10,11,0, 1,0,11,
		};

		Vector2f[] texCoord = new Vector2f[24];
		texCoord[0] = new Vector2f(0, 1*height);
		texCoord[1] = new Vector2f(0, 0);
		texCoord[2] = new Vector2f(1, 1*height);
		texCoord[3] = new Vector2f(1, 0);

		texCoord[4] = new Vector2f(0, 1*height);
		texCoord[5] = new Vector2f(0, 0);
		texCoord[6] = new Vector2f(1, 1*height);
		texCoord[7] = new Vector2f(1, 0);

		texCoord[8] = new Vector2f(0, 1*height);
		texCoord[9] = new Vector2f(0, 0);
		texCoord[10] = new Vector2f(1, 1*height);
		texCoord[11] = new Vector2f(1, 0);

		texCoord[12] = new Vector2f(0, 1*height);
		texCoord[13] = new Vector2f(0, 0);
		texCoord[14] = new Vector2f(1, 1*height);
		texCoord[15] = new Vector2f(1, 0);

		texCoord[16] = new Vector2f(0, 1*height);
		texCoord[17] = new Vector2f(0, 0);
		texCoord[18] = new Vector2f(1, 1*height);
		texCoord[19] = new Vector2f(1, 0);

		texCoord[20] = new Vector2f(0, 1*height);
		texCoord[21] = new Vector2f(0, 0);
		texCoord[22] = new Vector2f(1, 1*height);
		texCoord[23] = new Vector2f(1, 0);

		this.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		this.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
		this.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indexes));
		this.setBuffer(VertexBuffer.Type.Normal, 3, BufferUtils.createFloatBuffer(normals));
		this.updateBound();
	}
	
}
