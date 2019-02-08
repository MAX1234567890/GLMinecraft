package co.megadodo.mc.gl;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class ChunkModel {
	
	public ArrayList<Vector3f> pos;
	public ArrayList<Vector2f> uv;
	public ArrayList<Integer> tris;
	public ArrayList<Float> ao;
	public Vector3f posOffset;
	
	public ChunkModel() {
		pos=new ArrayList<Vector3f>();
		uv=new ArrayList<Vector2f>();
		tris=new ArrayList<Integer>();
		ao=new ArrayList<Float>();
		posOffset=new Vector3f(0,0,0);
	}
	
	public void setPositionOffset(float x,float y,float z) {
		posOffset=new Vector3f(x,y,z);
	}
	
	public void addPosition(float x,float y,float z) {
		pos.add(new Vector3f(x,y,z).add(posOffset));
	}
	
	public void addUV(float u,float v) {
		uv.add(new Vector2f(u,v));
	}
	
	public void addAO(float a) {
		ao.add(a);
	}
	
	public void addTriangle(int a,int b,int c) {
		tris.add(a);
		tris.add(b);
		tris.add(c);
	}

}
