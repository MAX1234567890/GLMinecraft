package co.megadodo.mc;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import co.megadodo.mc.gl.Mesh;
import co.megadodo.mc.gl.Shader;
import co.megadodo.mc.gl.Text;

public class Minecraft {

	public static void main(String[] args) {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

		long window = glfwCreateWindow(1000, 1000, "Minecraft", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		
		float width, height;

		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			width=pWidth.get(0);
			height=pHeight.get(0);
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);


		
		GL.createCapabilities();
		
		System.out.println(glGetString(GL_VERSION));
		System.out.println(glGetString(GL_VENDOR));
		
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

		
		while (!glfwWindowShouldClose(window)) {
			//INIT FRAME
			Utils.clearGLError();
			glClearColor(1,1,1,1);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glEnable(GL_DEPTH_TEST);
			//END INIT FRAME
			
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

			txt.setText("Still rendering at time "+String.format("%.2f", (float)glfwGetTime()));
			txt.render(-1,1, 0.025f);
			
			shader.bind();
			shader.setMat4f("perspective", cam.getPerspective());
			shader.setMat4f("view", cam.getView());
//			shader.setMat4f("perspective", new Mat4f());
//			shader.setMat4f("view", new Mat4f());
			mesh.renderElements();
			
			
			//CAMERA
			
			//DIRECTION
			double[]xpos=new double[1];
			double[]ypos=new double[1];
			glfwGetCursorPos(window, xpos, ypos);
			Vector2f mouse=new Vector2f((float)xpos[0],(float)ypos[0]);
			cam.update(mouse);
			if(mouse.x<0||mouse.y<0||mouse.x>width||mouse.y>height)
//					window.setMouse(width/2,height/2);
				glfwSetCursorPos(window, width/2, height/2);
			glfwGetCursorPos(window, xpos, ypos);
			mouse=new Vector2f((float)xpos[0],(float)ypos[0]);
			cam.mouse=mouse;
			//END DIRECTION
			
			//MOVEMENT
			//END MOVEMENT
			
			//END CAMERA

			//LEAVE FRAME
			Utils.printGLError();

			glfwSwapBuffers(window);
			glfwPollEvents();
			//END LEAVE FRAME
		}
	}

}