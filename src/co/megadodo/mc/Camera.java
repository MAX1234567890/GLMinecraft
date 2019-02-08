package co.megadodo.mc;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import co.megadodo.mc.gl.Window;

public class Camera {
	
	public Vector3f pos;
	public Vector3f dir;
	
	public float rotX;
	public float rotY;
	
	/**
	 * Field of view (FOV) in degrees
	 */
	public float fov;
	
	public Camera() {
		pos=new Vector3f(0,0,0);
		dir=new Vector3f(0,0,0);
		
		rotX=0;
		rotY=0;
		
		fov=80;
		
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
//		return new Matrix4f().identity().lookAt(pos, new Vector3f(0,0,0).add(pos).add(dir), new Vector3f(0,1,0));
		return new Matrix4f().identity().lookAt(new Vector3f(pos).sub(new Vector3f(dir).mul(4)), new Vector3f(pos).add(dir), new Vector3f(0,1,0));
	}
	
	public void handleMouseMove(Vector2f diff) {

		float dx=Mathf.map(diff.y,-10,10,-Mathf.PI*0.5f,Mathf.PI*0.5f);
		float dy=Mathf.map(diff.x,-10,10,Mathf.PI,-Mathf.PI);

		rotX=0.01f*dx;
		rotY=0.01f*dy;

		if(rotX<-Mathf.PI*0.49)rotX=-Mathf.PI*0.49f;
		if(rotX> Mathf.PI*0.49)rotX= Mathf.PI*0.49f;

//		printf("%f,%f %f,%f %f,%f\n",diff.x,diff.y,rotX,rotY,dx,dy);

		dir=new Vector3f(0,0,1);
		dir.rotateX(rotX);
		dir.rotateY(rotY);
	}

}
