package co.megadodo.mc;

import co.megadodo.mc.block.Block;
import co.megadodo.mc.block.Chunk;

public class WorldGeneratorFlat extends WorldGenerator {
	
	public void init() {
		Mathf.setNoiseSeed(0);
	}
	
	public void generateData(int cx,int cz,Block[][][]data) {
		for(int x=0;x<Chunk.SIZE;x++) {
			for(int z=0;z<Chunk.SIZE;z++) {
				int rx=x+cx*Chunk.SIZE;
				int rz=z+cz*Chunk.SIZE;
				for(int y=0;y<Chunk.HEIGHT;y++) {
					if(y<5)data[x][y][z]=Block.stone;
					else if(y<7)data[x][y][z]=Block.dirt;
					else if(y==7)data[x][y][z]=Block.grass;
					
					Mathf.setNoiseScale(0.1f);
					if(Mathf.noise(rx, y, rz)<0)data[x][y][z]=null;
				}
			}
		}
		for(int x=0;x<Chunk.SIZE;x++) {
			for(int z=0;z<Chunk.SIZE;z++) {
				int rx=x+cx*Chunk.SIZE;
				int rz=z+cz*Chunk.SIZE;
				float hash=Mathf.hash2f(rx, rz);
				if(hash>0.999f) {
					for(int y=8;y<=12;y++) {
//						data[x][y][z]=Block.oakLog;
						replace(rx,y,rz,Block.oakLog);
					}
					for(int ox=-2;ox<=2;ox++) {
						for(int oz=-2;oz<=2;oz++) {
//							data[x+ox][12][z+oz]=Block.bricks;
							for(int oy=0;oy<5;oy++) {
//								if(Mathf.abs(ox)+Mathf.abs(oz)<oy)positions.add(new Pair<Vector3i, Block>(new Vector3i(rx+ox,12+5-oy,rz+oz), Block.oakLeaves));
								replace(rx+ox,12+5-oy,rz+oz,Block.oakLeaves);
							}
						}
					}
//					data[x][7][z]=Block.dirt;
//					positions.add(new Pair<Vector3i, Block>(new Vector3i(rx,7,rz), Block.dirt));
					replace(rx,7,rz,Block.dirt);
				}
			}
		}
	}

}
