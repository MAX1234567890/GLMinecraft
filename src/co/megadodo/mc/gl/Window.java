package co.megadodo.mc.gl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

public class Window {
	
	public long ptr;
	public int width;
	public int height;
	
	private static boolean initDone=false;
	
	public Window() {
		if(!initDone) {
			initDone=true;
			GLFWErrorCallback.createPrint(System.err);
			if(!glfwInit()) {
				throw new RuntimeException("Unable to initialize GLFW.");
			}
		}
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		ptr=glfwCreateWindow(1000, 1000, "Minecraft", NULL, NULL);

		glfwMakeContextCurrent(ptr);
		glfwSwapInterval(1);
		glfwShowWindow(ptr);
		
		GL.createCapabilities();
		
		updateSize();
		
		System.out.println("Window initialized");
	}
	
	public Vector2f getMouse() {
		double[]x=new double[1];
		double[]y=new double[1];
		glfwGetCursorPos(ptr, x, y);
		return new Vector2f((float)x[0],(float)y[0]);
	}
	
	public void updateSize() {
		int[]w=new int[1];
		int[]h=new int[1];
		glfwGetWindowSize(ptr, w, h);
		width=w[0];
		height=h[0];
	}
	
	public boolean shouldContinue() {
		return !glfwWindowShouldClose(ptr);
	}
	
	public void endFrame() {
		glfwSwapBuffers(ptr);
		glfwPollEvents();
	}

}
