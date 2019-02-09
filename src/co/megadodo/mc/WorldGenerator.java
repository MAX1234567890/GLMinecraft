package co.megadodo.mc;

import java.util.ArrayList;

import org.joml.Vector3i;

import co.megadodo.mc.block.Block;

public abstract class WorldGenerator {

	public ArrayList<Pair<Vector3i, Block>> replacements=new ArrayList<Pair<Vector3i,Block>>();
	public void replace(int x,int y,int z,Block b) {
		replacements.add(new Pair<Vector3i,Block>(new Vector3i(x,y,z),b));
	}
	public abstract void init();
	public abstract void generateData(int cx,int cz,Block[][][]data);

}
