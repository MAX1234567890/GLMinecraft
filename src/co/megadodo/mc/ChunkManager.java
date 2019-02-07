package co.megadodo.mc;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3i;

import co.megadodo.mc.block.Chunk;
import co.megadodo.mc.gl.Shader;
import co.megadodo.mc.gl.Texture;

public class ChunkManager {

	public Map<Vector2i, Chunk> chunks;
	public Shader shader;
	public Texture texture;
	
	public static Vector2i getPosInChunk(Vector2i worldPos) {
		while(worldPos.x<0)worldPos.x+=Chunk.SIZE;
		while(worldPos.y<0)worldPos.y+=Chunk.SIZE;
		while(worldPos.x>=Chunk.SIZE)worldPos.x-=Chunk.SIZE;
		while(worldPos.y>=Chunk.SIZE)worldPos.y-=Chunk.SIZE;
		return worldPos;
	}
	
	public static Vector2i getChunkCoord(Vector2i worldPos) {
		Vector2i w=new Vector2i(worldPos).sub(getPosInChunk(new Vector2i(worldPos)));
		return new Vector2i(w.x/Chunk.SIZE,w.y/Chunk.SIZE);
	}

	public static Vector3i getPosInChunk(Vector3i worldPos) {
		Vector2i p=getPosInChunk(new Vector2i(worldPos.x,worldPos.z));
		return new Vector3i(p.x,worldPos.y,p.y);
	}

	public static Vector3i getChunkCoord(Vector3i worldPos) {
		Vector2i p=getChunkCoord(new Vector2i(worldPos.x,worldPos.z));
		return new Vector3i(p.x,worldPos.y,p.y);
	}
	
	public ChunkManager() {
		chunks=new HashMap<Vector2i, Chunk>();
		
		shader=new Shader("chunk");
		texture=new Texture("atlas-01.png");
	}
	
	public Chunk instantiateChunk(int cx,int cz) {
//		Vector2i v=new Vector2i(cx,cz);
//		if(!chunks.containsKey(v)) {
//			Chunk c=new Chunk();
//			c.chunkManager=this;
//			c.coord=new Vector2i(cx,cz);
//			c.genTerrain();
//			c.createMesh();
//			chunks.put(v, c);
//			return c;
//		}
//		return chunks.get(v);
		Chunk c=loadChunk(cx,cz);
		c.createMesh();
		return c;
	}
	
	public Chunk loadChunk(int cx,int cz) {
		Vector2i v=new Vector2i(cx,cz);
		if(!chunks.containsKey(v)) {
			Chunk c=new Chunk();
			c.chunkManager=this;
			c.coord=new Vector2i(cx,cz);
			c.genTerrain();
			chunks.put(v, c);
			return c;
		}
		return chunks.get(v);
	}
	
	public void update(Camera cam) {
		
	}
	
	public Chunk getChunk(Vector2i v) {
		Chunk c=chunks.get(v);
		if(c==null) {
			return loadChunk(v.x,v.y);
		}
		return c;
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
			if(c.isRenderable()) {
				c.render();
			}
		}
	}
	
	
}
