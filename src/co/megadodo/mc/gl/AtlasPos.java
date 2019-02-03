package co.megadodo.mc.gl;

public class AtlasPos {
	
	public float x,y,w,h;
	
	public AtlasPos(float x,float y,float w,float h) {
		this.x=x/w;
		this.y=y/h;
		this.w=1.0f/w;
		this.h=1.0f/h;
	}

}
