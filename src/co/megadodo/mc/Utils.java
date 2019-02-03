package co.megadodo.mc;

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

public class Utils {
	
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
