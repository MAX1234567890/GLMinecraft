package co.megadodo.mc;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector2i;

import co.megadodo.mc.block.Chunk;
import co.megadodo.mc.gl.Shader;
import co.megadodo.mc.gl.Texture;

public class ChunkManager {

	public Map<Vector2i, Chunk> chunks;
	public Shader shader;
	public Texture texture;
	
	public ChunkManager() {
		chunks=new HashMap<Vector2i, Chunk>();
		
		shader=new Shader("chunk");
		texture=new Texture("atlas-01.png");
	}
	
	public void instantiateChunk(int cx,int cz) {
		Vector2i v=new Vector2i(cx,cz);
		if(!chunks.containsKey(v)) {
			Chunk c=new Chunk();
			c.createMesh();
			chunks.put(v, c);
		}
	}
	
	public void render(Camera cam) {
		shader.bind();
		texture.bind(0);
		shader.setSampler("tex", 0);
		
		shader.setMat4f("perspective", cam.getPerspective());
		shader.setMat4f("view", cam.getView());
		shader.setMat4f("model", new Matrix4f().identity());
		
		for(Map.Entry<Vector2i, Chunk> entry: chunks.entrySet()) {
			Vector2i cpos=entry.getKey();
			Chunk c=entry.getValue();
			c.render();
		}
	}
	
	
}
