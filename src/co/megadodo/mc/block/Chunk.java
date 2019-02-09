package co.megadodo.mc.block;

import org.joml.Vector2i;
import org.joml.Vector3i;

import co.megadodo.mc.ChunkManager;
import co.megadodo.mc.Mathf;
import co.megadodo.mc.gl.AtlasPos;
import co.megadodo.mc.gl.ChunkModel;
import co.megadodo.mc.gl.Mesh;

public class Chunk {
	
	public static final int SIZE=16;
	public static final int HEIGHT=256;
	
	public Vector2i coord;
	
	public Block[][][]data;
	public float[][][]aoData;
	
	public Chunk() {
		data=new Block[SIZE][HEIGHT][SIZE];
		aoData=new float[SIZE][HEIGHT][SIZE];
		for(int x=0;x<SIZE;x++) {
			for(int y=0;y<HEIGHT;y++) {
				for(int z=0;z<SIZE;z++) {
					data[x][y][z]=null;
					aoData[x][y][z]=0;
				}
			}
		}
	}
	
	@Deprecated
	public void genTerrain() {
		for(int x=0;x<SIZE;x++) {
			for(int z=0;z<SIZE;z++) {
				int rx=coord.x*SIZE+x;
				int rz=coord.y*SIZE+z;
				int h=(int)(10+5*Mathf.cos(0.01f*rx*rz));
//				int h=rx+rz;
				for(int y=0;y<HEIGHT;y++) {
					if(y<h-2)data[x][y][z]=Block.stone;
					else if(y<h)data[x][y][z]=Block.dirt;
					else if(y==h)data[x][y][z]=Block.grass;
					else data[x][y][z]=null;
				}
			}
		}
	}
	
	public Mesh mesh=null;
	
	public void createMesh(boolean updated) {
		if(mesh==null) {
			mesh=new Mesh();
			mesh.initForModel();
		}
		mesh.setModel(getChunkMeshModel(updated));
	}
	
	public void addUV(ChunkModel cm,AtlasPos ap) {
		cm.addUV(ap.x, ap.y);
		cm.addUV(ap.x+ap.w, ap.y);
		cm.addUV(ap.x, ap.y+ap.h);
		cm.addUV(ap.x+ap.w, ap.y+ap.h);
	}
	
	public void addTriangleFace(ChunkModel cm,boolean flip) {
		int i=cm.pos.size();
		if(flip) {
			cm.addTriangle(i, i+1, i+3);
			cm.addTriangle(i, i+2, i+3);
		}else {
			cm.addTriangle(i, i+1, i+2);
			cm.addTriangle(i+1, i+2, i+3);
		}
	}
	
	public ChunkManager chunkManager;
	
	public boolean isEmptyLocal(int x,int y,int z) {
		if(x<0||z<0||x>=SIZE||z>=SIZE) {
			throw new RuntimeException("isEmptyLocal called with out-of-bounds arguments: ["+x+", "+y+", "+z+"].  Use isEmptyRelative instead.");
		}
		if(y<0||y>=HEIGHT)return true;
		return data[x][y][z]==null;
	}
	
	public boolean isEmptyRelative(int x,int y,int z) {
		Vector2i c=ChunkManager.getChunkCoord(new Vector2i(x+coord.x*SIZE,z+coord.y*SIZE));
		Chunk ch=chunkManager.getChunk(c);
		Vector3i p=ChunkManager.getPosInChunk(new Vector3i(x,y,z));
		return ch.isEmptyLocal(p.x, p.y, p.z);
	}
	
	private boolean hasCalcAO=false;
	private float calcAO(int x,int y,int z) {
		hasCalcAO=true;
		float f=0;
		int o=0;
		float n=0;
		for(int i=-o-1;i<=o;i++) {
			for(int j=-o-1;j<=o;j++) {
				for(int k=-o-1;k<=o;k++) {
					if(isEmptyRelative(x+i, y+j, z+k)) {
						f+=1;
					}
					n++;
				}
			}
		}
		return f/n;
	}
	
	public void calcAOData() {
		for(int x=0;x<SIZE;x++) {
			for(int y=0;y<HEIGHT;y++) {
				for(int z=0;z<SIZE;z++) {
					aoData[x][y][z]=calcAO(x,y,z);
				}
			}
		}
	}
	
	public float accessAOLocal(int x,int y,int z) {
		if(x<0||z<0||x>=SIZE||z>=SIZE) {
			throw new RuntimeException("isEmptyLocal called with out-of-bounds arguments: ["+x+", "+y+", "+z+"].  Use isEmptyRelative instead.");
		}
		if(y<0||y>=HEIGHT)return 0;
//		if(!hasCalcAO)calcAOData();//lazy load ao data
//		return aoData[x][y][z];
		return calcAO(x,y,z);
	}
	
	public float accessAORelative(int x,int y,int z) {
		Vector2i c=ChunkManager.getChunkCoord(new Vector2i(x+coord.x*SIZE,z+coord.y*SIZE));
		Chunk ch=chunkManager.getChunk(c);
		Vector3i p=ChunkManager.getPosInChunk(new Vector3i(x,y,z));
		return ch.accessAOLocal(p.x, p.y, p.z);
	}
	
	ChunkModel getChunkMeshModel(boolean updated) {
		ChunkModel cm=new ChunkModel();
		cm.setPositionOffset(coord.x*SIZE,0,coord.y*SIZE);
		
		if(updated) {
			calcAOData();
		}
		
		for(int x=0;x<SIZE;x++) {
			for(int y=0;y<HEIGHT;y++) {
				for(int z=0;z<SIZE;z++) {
					if(data[x][y][z]!=null) {
						Block b=data[x][y][z];
						
						if(isEmptyRelative(x-1,y,z)) {
							//xmi
							float a00=accessAORelative(x, y, z);
							float a01=accessAORelative(x, y, z+1);
							float a11=accessAORelative(x, y+1, z+1);
							float a10=accessAORelative(x, y+1, z);
							addTriangleFace(cm,a00+a11>a01+a10);
							cm.addPosition(x, y, z);
							cm.addPosition(x, y, z+1);
							cm.addPosition(x, y+1, z);
							cm.addPosition(x, y+1, z+1);
							cm.addAO(accessAORelative(x,y,z));
							cm.addAO(accessAORelative(x,y,z+1));
							cm.addAO(accessAORelative(x,y+1,z));
							cm.addAO(accessAORelative(x,y+1,z+1));
							addUV(cm,b.xmi);
						}

						if(isEmptyRelative(x+1,y,z)) {
							//xpl
							float a00=accessAORelative(x+1, y, z);
							float a01=accessAORelative(x+1, y, z+1);
							float a11=accessAORelative(x+1, y+1, z+1);
							float a10=accessAORelative(x+1, y+1, z);
							addTriangleFace(cm,a00+a11>a01+a10);
							cm.addPosition(x+1, y, z);
							cm.addPosition(x+1, y, z+1);
							cm.addPosition(x+1, y+1, z);
							cm.addPosition(x+1, y+1, z+1);
							cm.addAO(accessAORelative(x+1,y,z));
							cm.addAO(accessAORelative(x+1,y,z+1));
							cm.addAO(accessAORelative(x+1,y+1,z));
							cm.addAO(accessAORelative(x+1,y+1,z+1));
							addUV(cm,b.xpl);
						}
						if(isEmptyRelative(x,y-1,z)) {
							//ymi
							float a00=accessAORelative(x,y,z);
							float a01=accessAORelative(x,y,z+1);
							float a11=accessAORelative(x+1,y,z+1);
							float a10=accessAORelative(x+1,y,z);
							addTriangleFace(cm,a00+a11>a01+a10);
							cm.addPosition(x, y, z);
							cm.addPosition(x+1, y, z);
							cm.addPosition(x, y, z+1);
							cm.addPosition(x+1, y, z+1);
							cm.addAO(accessAORelative(x,y,z));
							cm.addAO(accessAORelative(x+1,y,z));
							cm.addAO(accessAORelative(x,y,z+1));
							cm.addAO(accessAORelative(x+1,y,z+1));
							addUV(cm,b.ymi);
						}
						if(isEmptyRelative(x,y+1,z)) {
							//ypl
							float a00=accessAORelative(x,y+1,z);
							float a01=accessAORelative(x,y+1,z+1);
							float a11=accessAORelative(x+1,y+1,z+1);
							float a10=accessAORelative(x+1,y+1,z);
							addTriangleFace(cm,a00+a11>a01+a10);
							cm.addPosition(x, y+1, z);
							cm.addPosition(x+1, y+1, z);
							cm.addPosition(x, y+1, z+1);
							cm.addPosition(x+1, y+1, z+1);
							cm.addAO(accessAORelative(x,y+1,z));
							cm.addAO(accessAORelative(x+1,y+1,z));
							cm.addAO(accessAORelative(x,y+1,z+1));
							cm.addAO(accessAORelative(x+1,y+1,z+1));
							addUV(cm,b.ypl);
						}
						if(isEmptyRelative(x,y,z-1)) {
							//zmi
							float a00=accessAORelative(x,y,z);
							float a01=accessAORelative(x,y+1,z);
							float a11=accessAORelative(x+1,y+1,z);
							float a10=accessAORelative(x+1,y,z);
							addTriangleFace(cm,a00+a11>a01+a10);
							cm.addPosition(x, y, z);
							cm.addPosition(x+1, y, z);
							cm.addPosition(x, y+1, z);
							cm.addPosition(x+1, y+1, z);
							cm.addAO(accessAORelative(x,y,z));
							cm.addAO(accessAORelative(x+1,y,z));
							cm.addAO(accessAORelative(x,y+1,z));
							cm.addAO(accessAORelative(x+1,y+1,z));
							addUV(cm,b.zmi);
						}
						if(isEmptyRelative(x,y,z+1)) {
							//zpl
							float a00=accessAORelative(x,y,z+1);
							float a01=accessAORelative(x,y+1,z+1);
							float a11=accessAORelative(x+1,y+1,z+1);
							float a10=accessAORelative(x+1,y,z+1);
							addTriangleFace(cm,a00+a11>a01+a10);
							cm.addPosition(x, y, z+1);
							cm.addPosition(x+1, y, z+1);
							cm.addPosition(x, y+1, z+1);
							cm.addPosition(x+1, y+1, z+1);
							cm.addAO(accessAORelative(x,y,z+1));
							cm.addAO(accessAORelative(x+1,y,z+1));
							cm.addAO(accessAORelative(x,y+1,z+1));
							cm.addAO(accessAORelative(x+1,y+1,z+1));
							addUV(cm,b.zpl);
						}
					}
				}
			}
		}
		return cm;
	}
	
	public void render() {
		mesh.renderElements();
	}
	
	public boolean isRenderable() {
		return mesh!=null;
	}

}
