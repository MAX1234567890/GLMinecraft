package co.megadodo.mc;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.glfw.GLFW.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import co.megadodo.mc.gl.Viewport;

public class Utils {
	
	public static void setLineSmooth(boolean b) {
		if(b) {
			glEnable(GL_LINE_SMOOTH);
		}else {
			glDisable(GL_LINE_SMOOTH);
		}
	}
	
	public static void setLineWidth(float f) {
		glLineWidth(f);
	}
	
	public static String formatFloat(float f) {
		return String.format("%.3f", f);
	}
	
	public static void initGLFrame(Viewport bounds) {
		glViewport((int)bounds.getX(),(int)bounds.getY(),(int)bounds.getW(),(int)bounds.getH());
		glClearColor(1,1,1,1);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public static void setDepth(boolean v) {
		if(v) {
			glEnable(GL_DEPTH_TEST);
		}else {
			glDisable(GL_DEPTH_TEST);
		}
	}
	
	public static void printGL(boolean ext) {
		System.out.printf("|--- OPENGL CONTEXT INFORMATION ---|\n");
		System.out.printf("VENDOR ............... %s\n",glGetString(GL_VENDOR));
		System.out.printf("RENDERER ............. %s\n",glGetString(GL_RENDERER));
		System.out.printf("VERSION .............. %s\n",glGetString(GL_VERSION));
		System.out.printf("GLSL VERSION ......... %s\n",glGetString(GL_SHADING_LANGUAGE_VERSION));
		
		if(ext) {
			int num=glGetInteger(GL_NUM_EXTENSIONS);
			for(int i=0;i<num;i++) {
				System.out.printf("EXTENSION %03d ........ %s\n",i+1,glGetStringi(GL_EXTENSIONS, i));				
			}
		}
	}
	
	public static float getTime() {
		return (float)glfwGetTime();
	}
	
	public static float[] convertVec3fToArray(ArrayList<Vector3f>list) {
		float[]data=new float[list.size()*3];
		for(int i=0;i<list.size();i++) {
			data[i*3+0]=list.get(i).x;
			data[i*3+1]=list.get(i).y;
			data[i*3+2]=list.get(i).z;
		}
		return data;
	}
	
	public static float[] convertVec2fToArray(ArrayList<Vector2f>list) {
		float[]data=new float[list.size()*2];
		for(int i=0;i<list.size();i++) {
			data[i*2+0]=list.get(i).x;
			data[i*2+1]=list.get(i).y;
		}
		return data;
	}
	
	public static float[] convertFloatListToArray(ArrayList<Float>list) {
		float[]data=new float[list.size()];
		for(int i=0;i<list.size();i++) {
			data[i]=list.get(i);
		}
		return data;
	}
	
    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

	//https://github.com/LWJGL/lwjgl3/blob/2359eb7b5a9e7e629beae95d3cdab8e997c59039/modules/samples/src/test/java/org/lwjgl/demo/util/IOUtil.java#L38
    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    ;
                }
            }
        } else {
            try (
                InputStream source = Utils.class.getClassLoader().getResourceAsStream(resource);
                ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = BufferUtils.createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
                    }
                }
            }
        }

        buffer.flip();
        return buffer.slice();
    }
	
	public static String loadFile(String fn) {
		try {
			return new String(Files.readAllBytes(Paths.get(fn)),StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
			return "";
		}
	}
	
	public static void clearGLError() {
		glGetError();
	}
	
	public static void printGLError() {
		int i=glGetError();
		if(i!=0) {
			System.err.println("GLERROR "+i);
		}
	}

}
