package co.megadodo.mc;

import co.megadodo.mc.block.Block;

public abstract class WorldGenerator {
	
	public abstract void init();
	public abstract void generateData(int cx,int cz,Block[][][]data);

}
