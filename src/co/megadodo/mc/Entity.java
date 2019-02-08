package co.megadodo.mc;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import co.megadodo.mc.gl.Mesh;
import co.megadodo.mc.gl.Shader;
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
	
	public Shader shader;
	public Mesh mesh;
	
	public Entity(Vector3f pos,Vector3f s,boolean grav) {
		shader=new Shader("entity");
		mesh=new Mesh();
		mesh.addBuffer3f(0, new float[] {
				0,0,0,
				1,0,0,
				0,1,0,
				1,1,0,
				
				0,0,1,
				1,0,1,
				0,1,1,
				1,1,1,
				
				0,0,0,
				1,0,0,
				0,0,1,
				1,0,1,
				
				0,1,0,
				1,1,0,
				0,1,1,
				1,1,1,
				
				0,0,0,
				0,1,0,
				0,0,1,
				0,1,1,
				
				1,0,0,
				1,1,0,
				1,0,1,
				1,1,1
		});
		mesh.setIndices(new int[] {
				0,1,2,
				1,2,3,
				
				4,5,6,
				5,6,7,
				
				8,9,10,
				9,10,11,
				
				12,13,14,
				13,14,15,
				
				16,17,18,
				17,18,19,
				
				20,21,22,
				21,22,23
		});
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
//		p.y+=1;
//		p=new Vector3f(p).add(new Vector3f(s).mul(.5f));
//		p.x+=s.x/2;
//		p.y+=s.y/2;
//		p.z+=s.z/2;
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
		return basicVerifyMotion(cm, pos, size, new Vector3f(0,-0.1f,0)).equals(new Vector3f(0,0,0));
//		return false;
	}
	
	public Vector3f getTotalVel() {
		return new Vector3f(vel.x+userVel.x,vel.y+userVel.y,vel.z+userVel.z);
	}
	
	public void handleInput(Window w,ChunkManager cm) {
		System.out.println(vel.y);
		Vector3f f=cam.getForward();
		Vector3f r=cam.getRight();
		Vector3f u=new Vector3f(0,1,0);
		
		Vector3f motion=new Vector3f(0,0,0);
		Vector3f dv=new Vector3f(0,0,0);
		if(w.isKeyDown('W'))motion.add(new Vector3f(f).mul(forwardSpeed));
		if(w.isKeyDown('S'))motion.add(new Vector3f(f).mul(backSpeed));
		if(w.isKeyDown('A'))motion.add(new Vector3f(r).mul(-sideSpeed));
		if(w.isKeyDown('D'))motion.add(new Vector3f(r).mul(sideSpeed));
		if(w.wasKeyJustPressed(' ')&&isGrounded(cm))dv.add(new Vector3f(0,jumpSpeed,0));
		
		userVel=motion;
		vel.add(dv);
	}
	
	public void render(Camera c) {
		shader.bind();
		shader.setMat4f("perspective", c.getPerspective());
		shader.setMat4f("view",c.getView());
		shader.setMat4f("model", new Matrix4f().identity().rotate(Mathf.atan2(cam.dir.x,cam.dir.z), 0,1,0));
		shader.set3f("pos", pos.x, pos.y, pos.z);
		shader.set3f("size", size.x, size.y, size.z);
		mesh.renderElements();
	}
	
	public void update(ChunkManager cm) {
		cam.pos=new Vector3f(pos.x+size.x/2,pos.y+size.y*0.75f,pos.z+size.z/2);
		pos.add(basicVerifyMotion(cm,pos,size,getTotalVel().mul(dt)));
		
		if(!isGrounded(cm)) {
			vel.y-=15f*dt;
		}else {
			vel.y=0;
			userVel.y=0;
		}
		
		
	}
	
	
}
