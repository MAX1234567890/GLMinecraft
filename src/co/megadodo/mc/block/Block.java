package co.megadodo.mc.block;

import co.megadodo.mc.gl.AtlasPos;

public class Block {
	
	public static final Block grass=new Block(BlockAtlas.grassSide,   BlockAtlas.grassTop,BlockAtlas.grassSide,BlockAtlas.dirt);
	public static final Block dirt=new Block(BlockAtlas.dirt,    BlockAtlas.dirt);
	public static final Block stone=new Block(BlockAtlas.stone,    BlockAtlas.stone);
	
	public static final Block oakPlanks=new Block(BlockAtlas.oakPlanks, BlockAtlas.oakPlanks);
	public static final Block oakLog=new Block(BlockAtlas.oakLogSide,  BlockAtlas.oakLogTop,BlockAtlas.oakLogSide,BlockAtlas.oakLogTop);
	public static final Block oakLeaves=new Block(BlockAtlas.oakLeavesDark,   BlockAtlas.oakLeavesLight);
	
	public static final Block cobblestone=new Block(BlockAtlas.cobblestone,   BlockAtlas.cobblestone);
	
	public static final Block bricks=new Block(BlockAtlas.bricks,    BlockAtlas.bricks);
	
	public static final Block snowyDirt=new Block(BlockAtlas.snowSide,    BlockAtlas.snowTop,BlockAtlas.snowSide,BlockAtlas.dirt);
	public static final Block snow=new Block(BlockAtlas.snowTop,   BlockAtlas.snowTop);
	
	
	public AtlasPos xmi,xpl,ymi,ypl,zmi,zpl,sprite;
	
	public Block(AtlasPos sp,AtlasPos ap) {
		this(sp,ap,ap,ap,ap,ap,ap);
	}
	
	public Block(AtlasPos sp,AtlasPos top,AtlasPos side,AtlasPos bot) {
		this(sp,side,side,bot,top,side,side);
	}
	
	public Block(AtlasPos sp,AtlasPos xmi,AtlasPos xpl,AtlasPos ymi,AtlasPos ypl,AtlasPos zmi,AtlasPos zpl) {
		sprite=sp;
		this.xmi=xmi;
		this.xpl=xpl;
		this.ymi=ymi;
		this.ypl=ypl;
		this.zmi=zmi;
		this.zpl=zpl;
	}
	
}
