package co.megadodo.mc;

import org.joml.Vector3f;
import org.joml.Vector3i;

import co.megadodo.mc.gl.Window;

public class Entity {
	
	public Vector3f pos;
	public Vector3f vel;
	public Vector3f userVel;
	public boolean doGravity;
	public Vector3f size;
	
	public Camera cam;
	
	public float forwardSpeed;
	public float sideSpeed;
	public float backSpeed;
	public float jumpSpeed;
	
	public float dt;
	
	public Entity(Vector3f pos,Vector3f s,boolean grav) {
		this.pos=pos;
		size=s;
		vel=new Vector3f(0,0,0);
		userVel=new Vector3f(0,0,0);
		doGravity=grav;
		
		forwardSpeed=5f;
		sideSpeed=5f;
		backSpeed=-5f;
		jumpSpeed=10f;
		
		cam=new Camera();
	}
	
	public void setFrameMotion(Vector3f v) {
		userVel=new Vector3f(v);
	}

	private static boolean basicVerifyPosition(ChunkManager cm,Vector3f p,Vector3f s) {
		Vector3i mi=new Vector3i(Mathf.floor(p.x),Mathf.floor(p.y),Mathf.floor(p.z));
		Vector3i ma=new Vector3i(Mathf.floor(p.x+s.x),Mathf.floor(p.y+s.y),Mathf.floor(p.z+s.z));
		
		for(int x=mi.x;x<=ma.x;x++) {
			for(int y=mi.y;y<=ma.y;y++) {
				for(int z=mi.z;z<=ma.z;z++) {
					if(cm.getBlock(new Vector3i(x,y,z))!=null)return false;
				}
			}
		}
		return true;
	}
	
	private static Vector3f basicVerifyMotion(ChunkManager cm,Vector3f p,Vector3f s,Vector3f dir) {
		if(dir.x==0&&dir.y==0&&dir.z==0)return dir;
		Vector3f res=new Vector3f(dir);

		if(!basicVerifyPosition(cm,new Vector3f(p).add(dir.x,0,0),s))res.x=0;
		if(!basicVerifyPosition(cm,new Vector3f(p).add(0,dir.y,0),s))res.y=0;
		if(!basicVerifyPosition(cm,new Vector3f(p).add(0,0,dir.z),s))res.z=0;
		
		return res;
	}
	
	public boolean isGrounded(ChunkManager cm) {
		//dir=0 => true
		//dir!=0 => false
		return basicVerifyMotion(cm, pos, size, new Vector3f(0,-1,0)).equals(new Vector3f(0,0,0));
	}
	
	public Vector3f getTotalVel() {
		return new Vector3f(vel.x+userVel.x,vel.y+userVel.y,vel.z+userVel.z);
	}
	
	public void handleInput(Window w,ChunkManager cm) {
		Vector3f f=cam.getForward();
		Vector3f r=cam.getRight();
		Vector3f u=new Vector3f(0,1,0);
		
		Vector3f motion=new Vector3f(0,0,0);
		Vector3f dv=new Vector3f(0,0,0);
		if(w.isKeyDown('W'))motion.add(new Vector3f(f).mul(forwardSpeed*dt));
		if(w.isKeyDown('S'))motion.add(new Vector3f(f).mul(backSpeed*dt));
		if(w.isKeyDown('A'))motion.add(new Vector3f(r).mul(-sideSpeed*dt));
		if(w.isKeyDown('D'))motion.add(new Vector3f(r).mul(sideSpeed*dt));
		if(w.isKeyDown(' ')&&isGrounded(cm))dv.add(new Vector3f(0,jumpSpeed*dt,0));
		
		userVel=basicVerifyMotion(cm,pos,size,motion);
		vel.add(basicVerifyMotion(cm,pos,size,dv));
	}
	
	public void update(ChunkManager cm) {
		cam.pos=new Vector3f(pos.x+size.x/2,pos.y+size.y,pos.z+size.z/2);
		pos.add(getTotalVel());
		
		if(!isGrounded(cm)) {
			vel.y-=0.005f;
		}else {
			vel.y=0;
			userVel.y=0;
		}
		
		
	}
	
	
}
