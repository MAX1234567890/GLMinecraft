package co.megadodo.mc;

import org.joml.Vector3i;

public class Intersection {
	
	public Vector3i hit;
	public Vector3i prev;
	public Vector3i normal;
	public int dist;
	
	public Intersection(Vector3i hit,Vector3i prev,int dist) {
		this.hit=hit;
		this.prev=prev;
		this.normal=new Vector3i(prev.x-hit.x,prev.y-hit.y,prev.z-hit.z);
		this.dist=dist;
	}

}
