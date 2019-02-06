package co.megadodo.mc;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import co.megadodo.mc.gl.Window;

public class Camera {
	
	public Vector3f pos;
	public Vector3f dir;
	
	public Vector2f mouse;
	
	public float forwardSpeed;
	public float sideSpeed;
	public float backSpeed;
	public float vertSpeed;
	
	public float rotX;
	public float rotY;
	
	/**
	 * Field of view (FOV) in degrees
	 */
	public float fov;
	
	public float dt;
	
	public Camera() {
		pos=new Vector3f(0,0,0);
		dir=new Vector3f(0,0,0);
		
		rotX=0;
		rotY=0;
		
		forwardSpeed=3f;
		sideSpeed=2f;
		backSpeed=1f;
		vertSpeed=1;
		
		fov=80;
		
		mouse=new Vector2f(0,0);
		
	}
	
	public void handleInput(Window window) {
		if(window.isKeyDown('W'))moveForward();
		if(window.isKeyDown('S'))moveBackward();
		if(window.isKeyDown('A'))moveLeft();
		if(window.isKeyDown('D'))moveRight();
		if(window.isKeyDown(' '))moveUp();
		if(window.isKeyDown(Window.LSHIFT))moveDown();
	}
	
	public void move(Vector3f dir) {
		pos=pos.add(dir.mul(dt));
	}
	
	public void moveForward() {
		move(getForward().mul(forwardSpeed));
	}
	
	public void moveBackward() {
		move(getForward().negate().mul(backSpeed));
	}
	
	public void moveLeft() {
		move(getRight().negate().mul(sideSpeed));
	}
	
	public void moveRight() {
		move(getRight().mul(sideSpeed));
	}
	
	public void moveUp() {
		move(new Vector3f(0,vertSpeed,0));
	}
	
	public void moveDown() {
		move(new Vector3f(0,-vertSpeed,0));
	}
	
	public Vector3f getForward() {
		return new Vector3f(dir.x,0,dir.z).normalize();
	}
	
	public Vector3f getRight() {
		return getForward().rotateY(-1.5707963268f);
	}
	
	public Matrix4f getPerspective() {
		return new Matrix4f().identity().perspective((float)Math.toRadians(fov), 1, 0.01f, 100.0f);
	}
	
	public Matrix4f getView() {
		return new Matrix4f().identity().lookAt(pos, new Vector3f(0,0,0).add(pos).add(dir), new Vector3f(0,1,0));
	}
	
	public void update(Vector2f newMouse) {
		Vector2f diff=new Vector2f(newMouse).sub(mouse);
		mouse=new Vector2f(newMouse);

		float dx=Mathf.map(diff.y,-10,10,-Mathf.PI*0.5f,Mathf.PI*0.5f);
		float dy=Mathf.map(diff.x,-10,10,Mathf.PI,-Mathf.PI);

		rotX+=0.01*dx;
		rotY+=0.01*dy;

		if(rotX<-Mathf.PI*0.49)rotX=-Mathf.PI*0.49f;
		if(rotX> Mathf.PI*0.49)rotX= Mathf.PI*0.49f;

//		printf("%f,%f %f,%f %f,%f\n",diff.x,diff.y,rotX,rotY,dx,dy);

		dir=new Vector3f(0,0,1);
		dir.rotateX(rotX);
		dir.rotateY(rotY);

//		if(isnanf(rotX))rotX=0;
//		if(isnanf(rotY))rotY=0;
//
//		if(isnanf(camDir.x)||isnanf(camDir.y)||isnanf(camDir.z))camDir=glm::vec3(1,0,0);
	}

}
