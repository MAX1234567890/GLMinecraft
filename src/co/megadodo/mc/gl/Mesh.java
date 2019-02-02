package co.megadodo.mc.gl;

import static org.lwjgl.opengl.GL45.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.megadodo.mc.Utils;
import co.megadodo.mc.math.Vec2f;
import co.megadodo.mc.math.Vec3f;

public class Mesh {
	
	public int id;
	public Map<Integer, Integer> buffers;
	public int ebo;
	public int eboSize;
	
	public Mesh() {
		buffers=new HashMap<Integer, Integer>();
		
		id=glGenVertexArrays();
		glBindVertexArray(id);
		
		ebo=glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,new int[] {},GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
		eboSize=0;
		
		glBindVertexArray(0);
	}
	
	public void setIndices(ArrayList<Integer> list) {
		int[]data=new int[list.size()];
		for(int i=0;i<list.size();i++) {
			data[i]=list.get(i);
		}
		setIndices(data);
	}
	public void setIndices(int[]data) {
		eboSize=data.length;
		glBindVertexArray(id);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,data,GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
		
		glBindVertexArray(0);
	}
	
	public void addBuffer3f(int attrib, ArrayList<Vec3f> list) {
		addBuffer3f(attrib,Utils.convertVec3fToArray(list));
	}
	public void addBuffer3f(int attrib,float[]data) {
		glBindVertexArray(id);
		int vbo=glGenBuffers();
		buffers.put(attrib, vbo);
		
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		glBufferData(GL_ARRAY_BUFFER,data,GL_STATIC_DRAW);
		glVertexAttribPointer(attrib, 3, GL_FLOAT, false, 3*Float.BYTES, 0);
		glEnableVertexAttribArray(attrib);
		glBindBuffer(GL_ARRAY_BUFFER,0);
		
		glBindVertexArray(0);
	}
	
	public void setBuffer3f(int attrib, ArrayList<Vec3f> list) {
		setBuffer3f(attrib,Utils.convertVec3fToArray(list));
	}
	public void setBuffer3f(int attrib,float[]data) {
		glBindVertexArray(id);
		
		int vbo=buffers.get(attrib);
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		glBufferData(GL_ARRAY_BUFFER,data,GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER,0);
		
		glBindVertexArray(0);
	}
	
	public void renderElements() {
		glBindVertexArray(id);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ebo);
		glDrawElements(GL_TRIANGLES,eboSize,GL_UNSIGNED_INT,0);
	}
	

}
