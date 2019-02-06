package co.megadodo.mc.block;

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
					data[x][y][z]=(y==0?new Block():null);
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
	
	ChunkModel getChunkMeshModel() {
		ChunkModel cm=new ChunkModel();
		cm.addPosition(0, 0, 0);
		cm.addPosition(1, 0, 0);
		cm.addPosition(0, 1, 0);
		cm.addUV(0, 0);
		cm.addUV(1, 0);
		cm.addUV(0, 1);
		cm.addTriangle(0, 1, 2);
		return cm;
	}
	
	public void render() {
		mesh.renderElements();
	}

}
