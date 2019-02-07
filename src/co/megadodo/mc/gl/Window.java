package co.megadodo.mc.gl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

public class Window implements Viewport {
	
	public float getX() {
		return 0;
	}
	
	public float getY() {
		return 0;
	}
	
	public float getW() {
		return fboWidth;
	}
	
	public float getH() {
		return fboHeight;
	}
	
	public long ptr;
	public int width;
	public int height;
	public int fboWidth;
	public int fboHeight;
	
	public static final char LSHIFT=GLFW_KEY_LEFT_SHIFT;
	
	private static boolean initDone=false;
	
	private ArrayList<Character> keysDown;
	private ArrayList<Character> justPressed;
	private ArrayList<Character> justReleased;
	
	private ArrayList<MouseMoveCallback> mouseMove;
	
	private void fireMouseMove() {
		Vector2f m=getMouse();
		for(MouseMoveCallback mm:mouseMove) {
			mm.onMouseMove(this,	 m.x, m.y);
		}
	}
	
	public void addOnMouseMove(MouseMoveCallback mm) {
		mouseMove.add(mm);
	}
	
	public boolean isKeyDown(char c) {
		return keysDown.contains(c);
	}
	
	public boolean wasKeyJustPressed(char c) {
		return justPressed.contains(c);
	}
	
	public boolean wasKeyJustReleased(char c) {
		return justReleased.contains(c);
	}
	
	public void hideMouse() {
		glfwSetInputMode(ptr	, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}

	private void onKey(int key,int action,int mods) {
		Character k=(char) key;
		if(action==GLFW_PRESS) {
//			System.out.printf("Pressed %c\n",k);
			justPressed.add(k);
			keysDown.add(k);
		}else if(action==GLFW_RELEASE) {
//			System.out.printf("Released %c\n",k);
			justReleased.add(k);
			keysDown.remove(k);
		}else if(action==GLFW_REPEAT) {
//			System.out.printf("Repeat %c\n",k);
		}
	}
	
	public void startFrame() {
		justPressed.clear();
		justReleased.clear();
		updateSize();
		updateFBOSize();
	}
	
	public void close() {
		glfwSetWindowShouldClose(ptr, true);
	}
	
	public void setMouse(float x,float y) {
		glfwSetCursorPos(ptr, x, y);
	}
	
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
		
		
		Window self=this;
		
		keysDown=new ArrayList<Character>();
		justPressed=new ArrayList<Character>();
		justReleased=new ArrayList<Character>();
		
		mouseMove=new ArrayList<MouseMoveCallback>();
		
		hideMouse();
		
		glfwSetKeyCallback(ptr, new GLFWKeyCallback() {
			
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				self.onKey(key,action,mods);
			}
		});
		
		glfwSetCursorPosCallback(ptr, new GLFWCursorPosCallback() {
			
			@Override
			public void invoke(long window, double xpos, double ypos) {
				self.fireMouseMove();
			}
		});

		glfwMakeContextCurrent(ptr);
		glfwSwapInterval(1);
		glfwShowWindow(ptr);
		
		GL.createCapabilities();
		
		updateSize();
		updateFBOSize();
		
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
	
	public void updateFBOSize() {
		int[]w=new int[1];
		int[]h=new int[1];
		glfwGetFramebufferSize(ptr, w, h);
		fboWidth=w[0];
		fboHeight=h[0];
	}
	
	public boolean shouldContinue() {
		return !glfwWindowShouldClose(ptr);
	}
	
	public void endFrame() {
		glfwSwapBuffers(ptr);
		glfwPollEvents();
	}

}
