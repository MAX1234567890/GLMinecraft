package co.megadodo.mc.block;

import co.megadodo.mc.gl.AtlasPos;

public class Block {
	
	public AtlasPos xmi,xpl,ymi,ypl,zmi,zpl;
	
	public Block() {
		xmi=xpl=ymi=ypl=zmi=zpl=BlockAtlas.dirt;
	}
	
}
