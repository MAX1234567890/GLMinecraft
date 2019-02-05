package co.megadodo.mc;

import org.joml.Vector2f;
import org.joml.Vector3f;

import co.megadodo.mc.gl.Framebuffer;
import co.megadodo.mc.gl.Mesh;
import co.megadodo.mc.gl.Shader;
import co.megadodo.mc.gl.Text;
import co.megadodo.mc.gl.Window;

public class Minecraft {

	public static void main(String[] args) {
		Window window=new Window();
		
		Utils.printGL(true);
		
		Mesh mesh=new Mesh();
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
				1,1,1,
		});
		mesh.addBuffer3f(1, new float[] {
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
				1,1,1,
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
				21,22,23,
		});		
		Shader shader=new Shader("test");
		

		Camera cam=new Camera();
		cam.pos=new Vector3f(2,2,2);
		cam.dir=new Vector3f(0,0,1);
		
		Utils.printGLError();
		
		Mesh quad=new Mesh();
		quad.addBuffer2f(0, new float[] {
				0,0,
				1,0,
				0,1,
				1,1
		});
		quad.setIndices(new int[] {
				0,1,2,
				1,2,3
		});
		
		Shader postprocess=new Shader("postprocess");

		
		float lastTime=Utils.getTime();
		
		Framebuffer fboWorld=new Framebuffer();

		Text txt=new Text(fboWorld);

		while (window.shouldContinue()) {
			float curTime=Utils.getTime();
			float dt=curTime-lastTime;
			lastTime=curTime;
			
			cam.dt=dt;
			
			window.startFrame();
			Utils.clearGLError();
			
			fboWorld.start();
			Utils.initGLFrame(fboWorld);
			Utils.setDepth(true);
			
			shader.bind();
			shader.setMat4f("perspective", cam.getPerspective());
			shader.setMat4f("view", cam.getView());
			mesh.renderElements();
			
			fboWorld.end();
			
			
			Utils.initGLFrame(window);
			Utils.setDepth(false);

			postprocess.bind();
			fboWorld.bindColor(0);
			postprocess.setSampler("tex", 0);
			quad.renderElements();
			
			
			Vector2f mouse=window.getMouse();
			cam.update(mouse);
//			if(mouse.x<0||mouse.y<0||mouse.x>window.width||mouse.y>window.height)
//					window.setMouse(window.width/2,window.height/2);
			cam.mouse=window.getMouse();

			txt.setText("Minecraft\n"
					 + "Camera position: ["+Utils.formatFloat(cam.pos.x)+", "+Utils.formatFloat(cam.pos.y)+", "+Utils.formatFloat(cam.pos.z)+"]\n"
					 + "Camera direction: ["+Utils.formatFloat(cam.dir.x)+", "+Utils.formatFloat(cam.dir.y)+", "+Utils.formatFloat(cam.dir.z)+"]\n");
			txt.render(-1+0.0025f,1-0.0025f, 0.03f);
			
			
			if(window.isKeyDown('W'))cam.moveForward();
			if(window.isKeyDown('S'))cam.moveBackward();
			if(window.isKeyDown('A'))cam.moveLeft();
			if(window.isKeyDown('D'))cam.moveRight();
			if(window.isKeyDown(' '))cam.moveUp();
			if(window.isKeyDown(Window.LSHIFT))cam.moveDown();
			
			if(window.isKeyDown('/'))window.close();
			
			
			Utils.printGLError();

			window.endFrame();
		}
	}

}