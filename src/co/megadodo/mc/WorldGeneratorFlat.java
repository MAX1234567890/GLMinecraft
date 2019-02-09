package co.megadodo.mc;

import java.util.ArrayList;

import org.joml.Vector2i;
import org.joml.Vector3i;

import co.megadodo.mc.block.Block;
import co.megadodo.mc.block.Chunk;

public class WorldGeneratorFlat extends WorldGenerator {
	
	public void init() {
		positions=new ArrayList<Pair<Vector3i, Block>>();
	}
	
	public ArrayList<Pair<Vector3i, Block>> positions;
	
	public void generateData(int cx,int cz,Block[][][]data) {
		for(int x=0;x<Chunk.SIZE;x++) {
			for(int z=0;z<Chunk.SIZE;z++) {
				for(int y=0;y<Chunk.HEIGHT;y++) {
					if(y<5)data[x][y][z]=Block.stone;
					else if(y<7)data[x][y][z]=Block.dirt;
					else if(y==7)data[x][y][z]=Block.grass;
				}
			}
		}
		for(int x=0;x<Chunk.SIZE;x++) {
			for(int z=0;z<Chunk.SIZE;z++) {
				int rx=x+cx*Chunk.SIZE;
				int rz=z+cz*Chunk.SIZE;
				float hash=Mathf.hash2f(rx, rz);
				if(hash>0.99f) {
					for(int y=8;y<=12;y++) {
//						data[x][y][z]=Block.oakLog;
						positions.add(new Pair<Vector3i, Block>(new Vector3i(rx,y,rz), Block.oakLog));
					}
					for(int ox=-2;ox<=2;ox++) {
						for(int oz=-2;oz<=2;oz++) {
//							data[x+ox][12][z+oz]=Block.bricks;
							for(int oy=0;oy<5;oy++) {
								if(Mathf.abs(ox)+Mathf.abs(oz)<oy)positions.add(new Pair<Vector3i, Block>(new Vector3i(rx+ox,12+5-oy,rz+oz), Block.oakLeaves));
							}
						}
					}
//					data[x][7][z]=Block.dirt;
					positions.add(new Pair<Vector3i, Block>(new Vector3i(rx,7,rz), Block.dirt));
				}
			}
		}
		
		for(Pair<Vector3i,Block>pos:positions) {
			Vector2i chunkCoord=ChunkManager.getChunkCoord(new Vector2i(pos.k.x,pos.k.z));
			if(chunkCoord.x==cx&&chunkCoord.y==cz) {
				Vector3i p=ChunkManager.getPosInChunk(pos.k);
				data[p.x][p.y][p.z]=pos.v;
			}
		}
	}

}
