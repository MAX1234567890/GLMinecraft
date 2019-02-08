package co.megadodo.mc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import co.megadodo.mc.block.Block;
import co.megadodo.mc.block.Chunk;
import co.megadodo.mc.gl.Shader;
import co.megadodo.mc.gl.Texture;

public class ChunkManager {

	public Map<Vector2i, Chunk> chunks;
	public Shader shader;
	public Texture texture;
	public ArrayList<Vector2i> chunksToAdd;
	public ArrayList<Vector2i> chunksToRem;
	public ArrayList<Vector2i> chunksToRemesh;
	
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
		
		chunksToAdd=new ArrayList<Vector2i>();
		chunksToRem=new ArrayList<Vector2i>();
		chunksToRemesh=new ArrayList<Vector2i>();
		
		shader=new Shader("chunk");
		texture=new Texture("atlas-02.png");
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
		c.createMesh(false);
//		remeshChunk(new Vector2i(cx,cz));
		return c;
	}
	
	public Chunk loadChunk(int cx,int cz) {
		Vector2i v=new Vector2i(cx,cz);
		if(!chunks.containsKey(v)) {
			Chunk c=new Chunk();
			c.chunkManager=this;
			c.coord=new Vector2i(cx,cz);
			chunks.put(v, c);
			c.genTerrain();
			return c;
		}
		return chunks.get(v);
	}
	
	public Chunk getChunk(Vector2i v) {
		Chunk c=chunks.get(v);
		if(c==null) {
			return loadChunk(v.x,v.y);
		}
		return c;
	}
	
	
	public void remeshChunk(Vector2i v) {
		Chunk c=loadChunk(v.x,v.y);
		c.createMesh(true);
	}
	
	
	public void update(Camera cam) {
		Vector2i camCoord=getChunkCoord(new Vector2i((int)cam.pos.x,(int)cam.pos.z));
		
		int o=5;
		for(int x=-o;x<=o;x++) {
			for(int y=-o;y<=o;y++) {
//				instantiateChunk(x+camCoord.x, y+camCoord.y);
				Vector2i v=new Vector2i(x+camCoord.x,y+camCoord.y);
				if(!chunksToAdd.contains(v)&&!chunks.containsKey(v)) {
					chunksToAdd.add(v);
				}
			}
		}
		
		for(int i=0;i<1&&chunksToAdd.size()>0;i++) {
			Vector2i v=chunksToAdd.get(0);
			chunksToAdd.remove(0);
			instantiateChunk(v.x,v.y);
		}
		
		for(int i=0;i<1&&chunksToRemesh.size()>0;i++) {
			Vector2i v=chunksToRemesh.get(0);
			chunksToRemesh.remove(0);
			remeshChunk(v);
		}
		
	}
	
	public void setAndRemeshBlock(Vector3i pos,Block b) {
		Vector2i worldPos=new Vector2i(pos.x,pos.z);
		Vector2i chunkCoord=getChunkCoord(worldPos);
		Vector3i posInChunk=getPosInChunk(pos);
		Chunk c=getChunk(chunkCoord);
		if(posInChunk.y<0||posInChunk.y>=Chunk.HEIGHT)return;
		if(c.data[posInChunk.x][posInChunk.y][posInChunk.z]==b)return;
		c.data[posInChunk.x][posInChunk.y][posInChunk.z]=b;

		if(posInChunk.x==0)startRemesh(new Vector2i(chunkCoord.x-1,chunkCoord.y));
		if(posInChunk.z==0)startRemesh(new Vector2i(chunkCoord.x,chunkCoord.y-1));
		if(posInChunk.x==Chunk.SIZE-1)startRemesh(new Vector2i(chunkCoord.x+1,chunkCoord.y));
		if(posInChunk.z==Chunk.SIZE-1)startRemesh(new Vector2i(chunkCoord.x,chunkCoord.y+1));
		
		startRemesh(chunkCoord);
	}
	
	public void startRemesh(Vector2i v) {
		if(!chunksToRemesh.contains(v))chunksToRemesh.add(v);
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
	
	public int numRenderedChunks() {
		int i=0;
		for(Map.Entry<Vector2i, Chunk>entry:chunks.entrySet()) {
			if(entry.getValue().isRenderable())i++;
		}
		return i;
	}
	
	public Block getBlock(Vector3i worldPos) {
		Vector2i chunkCoord=getChunkCoord(new Vector2i(worldPos.x,worldPos.z));
		Chunk c=getChunk(chunkCoord);
		Vector3i posInChunk=getPosInChunk(worldPos);
		return c.data[posInChunk.x][posInChunk.y][posInChunk.z];
	}
	
	public Intersection intersectWorld(Vector3f start,Vector3f dir,int maxDist) {
		Vector3f end=new Vector3f(start.x+dir.x*maxDist,start.y+dir.y*maxDist,start.z+dir.z*maxDist);

		final float x1=start.x;
		final float y1=start.y;
		final float z1=start.z;

		final float x2=end.x;
		final float y2=end.y;
		final float z2=end.z;
		
		int i=Mathf.floor(x1);
		int j=Mathf.floor(y1);
		int k=Mathf.floor(z1);
		
		final int di = ((x1 < x2) ? 1 : ((x1 > x2) ? -1 : 0));
		final int dj = ((y1 < y2) ? 1 : ((y1 > y2) ? -1 : 0));
		final int dk = ((z1 < z2) ? 1 : ((z1 > z2) ? -1 : 0));
		
		final float dx=1.0f/Mathf.abs(x2-x1);
		final float dy=1.0f/Mathf.abs(y2-y1);
		final float dz=1.0f/Mathf.abs(z2-z1);
		
		final float minx=Mathf.floor(x1),maxx=minx+1;
		final float miny=Mathf.floor(y1),maxy=miny+1;
		final float minz=Mathf.floor(z1),maxz=minz+1;

		float tx=((x1>x2)?(x1-minx):(maxx-x1))*dx;
		float ty=((y1>y2)?(y1-miny):(maxy-y1))*dy;
		float tz=((z1>z2)?(z1-minz):(maxz-z1))*dz;
		
		ArrayList<Vector3i>list=new ArrayList<Vector3i>();
		
		for(int num=0;num<maxDist;num++) {
			if(j<0||j>=Chunk.HEIGHT)break;
			list.add(new Vector3i(i,j,k));
			if(tx<=ty&&tx<=tz) {
				tx+=dx;
				i+=di;
			}else if(ty<=tz) {
				ty+=dy;
				j+=dj;
			}else {
				tz+=dz;
				k+=dk;
			}
		}
		
		for(int ind=1;ind<list.size();ind++) {
			if(getBlock(list.get(ind))!=null) {
				Intersection inters=new Intersection(list.get(ind),list.get(ind-1),ind);
				return inters;
			}
		}
		
		
		return null;
	}
	
	
}
