package co.megadodo.mc.block;

import co.megadodo.mc.gl.AtlasPos;

public class Block {
	
	public static final Block grass=new Block(false,BlockAtlas.grassSide,   BlockAtlas.grassTop,BlockAtlas.grassSide,BlockAtlas.dirt);
	public static final Block dirt=new Block(false,BlockAtlas.dirt,    BlockAtlas.dirt);
	public static final Block stone=new Block(false,BlockAtlas.stone,    BlockAtlas.stone);
	
	public static final Block oakPlanks=new Block(false,BlockAtlas.oakPlanks, BlockAtlas.oakPlanks);
	public static final Block oakLog=new Block(false,BlockAtlas.oakLogSide,  BlockAtlas.oakLogTop,BlockAtlas.oakLogSide,BlockAtlas.oakLogTop);
	public static final Block oakLeaves=new Block(true,BlockAtlas.oakLeavesDark,   BlockAtlas.oakLeavesLight);
	
	public static final Block cobblestone=new Block(false,BlockAtlas.cobblestone,   BlockAtlas.cobblestone);
	
	public static final Block bricks=new Block(false,BlockAtlas.bricks,    BlockAtlas.bricks);
	
	public static final Block snowyDirt=new Block(false,BlockAtlas.snowSide,    BlockAtlas.snowTop,BlockAtlas.snowSide,BlockAtlas.dirt);
	public static final Block snow=new Block(false,BlockAtlas.snowTop,   BlockAtlas.snowTop);
	
	
	public AtlasPos xmi,xpl,ymi,ypl,zmi,zpl,sprite;
	
	public boolean transparent;
	
	public Block(boolean t,AtlasPos sp,AtlasPos ap) {
		this(t,sp,ap,ap,ap,ap,ap,ap);
	}
	
	public Block(boolean t,AtlasPos sp,AtlasPos top,AtlasPos side,AtlasPos bot) {
		this(t,sp,side,side,bot,top,side,side);
	}
	
	public Block(boolean t,AtlasPos sp,AtlasPos xmi,AtlasPos xpl,AtlasPos ymi,AtlasPos ypl,AtlasPos zmi,AtlasPos zpl) {
		transparent=t;
		sprite=sp;
		this.xmi=xmi;
		this.xpl=xpl;
		this.ymi=ymi;
		this.ypl=ypl;
		this.zmi=zmi;
		this.zpl=zpl;
	}
	
}
