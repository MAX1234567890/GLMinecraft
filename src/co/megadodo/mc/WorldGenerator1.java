package co.megadodo.mc;

import co.megadodo.mc.block.Block;
import co.megadodo.mc.block.Chunk;

public class WorldGenerator1 extends WorldGenerator {

	@Override
	public void init() {
		Mathf.setNoiseSeed(0);
	}

	@Override
	public void generateData(int cx, int cz, Block[][][] data) {
//		Mathf.setNoiseScale(0.05f);
		for(int x=0;x<Chunk.SIZE;x++) {
			for(int z=0;z<Chunk.SIZE;z++) {
				int rx=x+cx*Chunk.SIZE;
				int rz=z+cz*Chunk.SIZE;
				
				Mathf.setNoiseScale(0.1f);
				Mathf.setNoiseScale(0.01f*Mathf.noise(rx, rz));
				int h=(int)(50+30*Mathf.noise(rx, rz));
				
				for(int y=0;y<Chunk.HEIGHT;y++) {
					if(y<h-5) {
						data[x][y][z]=Block.stone;
					}else if(y<h) {
						data[x][y][z]=Block.dirt;
					}else if(y==h) {
						data[x][y][z]=Block.grass;
					}
				}
				
			}
		}
	}

}
