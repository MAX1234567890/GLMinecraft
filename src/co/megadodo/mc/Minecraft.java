package co.megadodo.mc;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import co.megadodo.mc.gl.Mesh;
import co.megadodo.mc.gl.Shader;

public class Minecraft {

	public static void main(String[] args) {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

		long window = glfwCreateWindow(1000, 1000, "Minecraft", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);


		
		GL.createCapabilities();
		
		System.out.println(glGetString(GL_VERSION));
		System.out.println(glGetString(GL_VENDOR));
		
		Mesh mesh=new Mesh();
		mesh.addBuffer3f(0, new float[] {
				0,0,0,
				1,0,0,
				0,1,0
		});
		mesh.addBuffer3f(1, new float[] {
				1,0,0,
				0,1,0,
				0,0,1
		});
		mesh.setIndices(new int[] {
				0,1,2
		});
		
		Shader shader=new Shader("test");
		

		Utils.printGLError();

		
		while (!glfwWindowShouldClose(window)) {
			Utils.clearGLError();
			glClearColor(1,1,1,1);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			mesh.setBuffer3f(0, new float[] {
					(float)Math.cos(glfwGetTime()*3),(float)Math.sin(glfwGetTime()*4),0,
					1,0,0,
					0,1,0
			});

			mesh.setBuffer3f(1, new float[] {
					1,0,0,
					0,1,0,
					0,0,5*(float)(.5+.5*Math.cos(5*glfwGetTime()))
			});
			
			shader.bind();
			mesh.renderElements();


			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}

}