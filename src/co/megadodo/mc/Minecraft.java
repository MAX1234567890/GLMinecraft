package co.megadodo.mc;

import static org.lwjgl.opengl.GL45.*;

import org.joml.Vector2f;
import org.joml.Vector3f;

import co.megadodo.mc.gl.Mesh;
import co.megadodo.mc.gl.Shader;
import co.megadodo.mc.gl.Text;
import co.megadodo.mc.gl.Window;

public class Minecraft {

	public static void main(String[] args) {
		Window window=new Window();
		
		Utils.printGL(true);
		
		Mesh mesh=new Mesh();
		mesh.addBuffer3f(0, new float[] {});
		mesh.addBuffer3f(1, new float[] {});
		mesh.setIndices(new int[] {});
		
		Shader shader=new Shader("test");
		
		Text txt=new Text();

		Camera cam=new Camera();
		cam.pos=new Vector3f(2,2,2);
		cam.dir=new Vector3f(cam.pos.x,cam.pos.y,cam.pos.z).normalize().negate();
		
		Utils.printGLError();

		
		while (window.shouldContinue()) {
			Utils.clearGLError();
			
			glClearColor(1,1,1,1);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glEnable(GL_DEPTH_TEST);
			
			
			
			
			mesh.setBuffer3f(0, new float[] {
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

			mesh.setBuffer3f(1, new float[] {
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

			txt.setText("Still rendering at time "+String.format("%.2f", Utils.getTime()));
			txt.render(-1,1, 0.025f);
			
			shader.bind();
			shader.setMat4f("perspective", cam.getPerspective());
			shader.setMat4f("view", cam.getView());
			mesh.renderElements();
			
			
			
			
			
			Vector2f mouse=window.getMouse();
			cam.update(mouse);
//			if(mouse.x<0||mouse.y<0||mouse.x>window.width||mouse.y>window.height)
//					window.setMouse(width/2,height/2);
			cam.mouse=window.getMouse();


			
			
			
			Utils.printGLError();

			window.endFrame();
		}
	}

}