package co.megadodo.mc.block;

import co.megadodo.mc.gl.AtlasPos;
import co.megadodo.mc.gl.ChunkModel;
import co.megadodo.mc.gl.Mesh;

public class Chunk {
	
	public static final int SIZE=16;
	public static final int HEIGHT=256;
	
	public Block[][][]data;
	
	public Chunk() {
		data=new Block[SIZE][HEIGHT][SIZE];
		for(int x=0;x<SIZE;x++) {
			for(int y=0;y<HEIGHT;y++) {
				for(int z=0;z<SIZE;z++) {
					if(y<4)data[x][y][z]=Block.stone;
					else if(y<6)data[x][y][z]=Block.dirt;
					else if(y==6)data[x][y][z]=Block.grass;
					else data[x][y][z]=null;
				}
			}
		}
	}
	
	public Mesh mesh=null;
	
	public void createMesh() {
		if(mesh==null) {
			mesh=new Mesh();
			mesh.initForModel();
		}
		mesh.setModel(getChunkMeshModel());
	}
	
	public void addUV(ChunkModel cm,AtlasPos ap) {
		cm.addUV(ap.x, ap.y);
		cm.addUV(ap.x+ap.w, ap.y);
		cm.addUV(ap.x, ap.y+ap.h);
		cm.addUV(ap.x+ap.w, ap.y+ap.h);
	}
	
	public void addTriangleFace(ChunkModel cm) {
		int i=cm.pos.size();
		cm.addTriangle(i, i+1, i+2);
		cm.addTriangle(i+1, i+2, i+3);
	}
	
	public boolean isEmptyLocal(int x,int y,int z) {
		if(x<0||y<0||z<0||x>=SIZE||y>=HEIGHT||z>=SIZE)return true;
		return data[x][y][z]==null;
	}
	
	ChunkModel getChunkMeshModel() {
		ChunkModel cm=new ChunkModel();
		
		for(int x=0;x<SIZE;x++) {
			for(int y=0;y<HEIGHT;y++) {
				for(int z=0;z<SIZE;z++) {
					if(data[x][y][z]!=null) {
						Block b=data[x][y][z];
						
						if(isEmptyLocal(x-1,y,z)) {
							//xmi
							addTriangleFace(cm);
							cm.addPosition(x, y, z);
							cm.addPosition(x, y, z+1);
							cm.addPosition(x, y+1, z);
							cm.addPosition(x, y+1, z+1);
							addUV(cm,b.xmi);
						}

						if(isEmptyLocal(x+1,y,z)) {
							//xpl
							addTriangleFace(cm);
							cm.addPosition(x+1, y, z);
							cm.addPosition(x+1, y, z+1);
							cm.addPosition(x+1, y+1, z);
							cm.addPosition(x+1, y+1, z+1);
							addUV(cm,b.xpl);
						}
						if(isEmptyLocal(x,y-1,z)) {
							//ymi
							addTriangleFace(cm);
							cm.addPosition(x, y, z);
							cm.addPosition(x+1, y, z);
							cm.addPosition(x, y, z+1);
							cm.addPosition(x+1, y, z+1);
							addUV(cm,b.ymi);
						}
						if(isEmptyLocal(x,y+1,z)) {
							//ypl
							addTriangleFace(cm);
							cm.addPosition(x, y+1, z);
							cm.addPosition(x+1, y+1, z);
							cm.addPosition(x, y+1, z+1);
							cm.addPosition(x+1, y+1, z+1);
							addUV(cm,b.ymi);
						}
						if(isEmptyLocal(x,y,z-1)) {
							//zmi
							addTriangleFace(cm);
							cm.addPosition(x, y, z);
							cm.addPosition(x+1, y, z);
							cm.addPosition(x, y+1, z);
							cm.addPosition(x+1, y+1, z);
							addUV(cm,b.zmi);
						}
						if(isEmptyLocal(x,y,z+1)) {
							//zpl
							addTriangleFace(cm);
							cm.addPosition(x, y, z+1);
							cm.addPosition(x+1, y, z+1);
							cm.addPosition(x, y+1, z+1);
							cm.addPosition(x+1, y+1, z+1);
							addUV(cm,b.zpl);
						}
					}
				}
			}
		}
		
//		cm.addPosition(0, 0, 0);
//		cm.addPosition(1, 0, 0);
//		cm.addPosition(0, 1, 0);
//		cm.addPosition(1, 1, 0);
//		addUV(cm,BlockAtlas.dirt);
//		cm.addTriangle(0, 1, 2);
//		cm.addTriangle(1,2,3);
		for(int i=0;i<10;i++) {
			System.out.println(cm.tris.get(i));
		}
		return cm;
	}
	
	public void render() {
		mesh.renderElements();
	}

}
