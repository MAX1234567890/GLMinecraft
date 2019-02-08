package co.megadodo.mc.block;

import co.megadodo.mc.gl.AtlasPos;

public class Block {
	
	public static final Block grass=new Block(BlockAtlas.grassTop,BlockAtlas.grassSide,BlockAtlas.dirt);
	public static final Block dirt=new Block(BlockAtlas.dirt);
	public static final Block stone=new Block(BlockAtlas.stone);
	
	public AtlasPos xmi,xpl,ymi,ypl,zmi,zpl;
	
	public Block(AtlasPos ap) {
		this(ap,ap,ap,ap,ap,ap);
	}
	
	public Block(AtlasPos top,AtlasPos side,AtlasPos bot) {
		this(side,side,bot,top,side,side);
	}
	
	public Block(AtlasPos xmi,AtlasPos xpl,AtlasPos ymi,AtlasPos ypl,AtlasPos zmi,AtlasPos zpl) {
		this.xmi=xmi;
		this.xpl=xpl;
		this.ymi=ymi;
		this.ypl=ypl;
		this.zmi=zmi;
		this.zpl=zpl;
	}
	
}
