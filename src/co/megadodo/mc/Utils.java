package co.megadodo.mc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import co.megadodo.mc.math.Vec3f;

import static org.lwjgl.opengl.GL45.*;

public class Utils {
	
	public static float[] convertVec3fToArray(ArrayList<Vec3f>list) {
		float[]data=new float[list.size()*3];
		for(int i=0;i<list.size();i++) {
			data[i*3+0]=list.get(i).x;
			data[i*3+1]=list.get(i).y;
			data[i*3+2]=list.get(i).z;
		}
		return data;
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
