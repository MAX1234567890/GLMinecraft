package co.megadodo.mc.gl;

import static org.lwjgl.opengl.GL45.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2f;
import org.joml.Vector3f;

import co.megadodo.mc.Utils;

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
	
	public void initForModel() {
		addBuffer3f(0, new float[] {});
		addBuffer2f(1, new float[] {});
		addBuffer1f(2, new float[] {});
		setIndices(new int[] {});
	}
	
	public void setModel(ChunkModel model) {
		setBuffer3f(0, model.pos);
		setBuffer2f(1, model.uv);
		setBuffer1f(2, model.ao);
		setIndices(model.tris);
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
	

	public void addBuffer3f(int attrib, ArrayList<Vector3f> list) {
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
	
	public void setBuffer3f(int attrib, ArrayList<Vector3f> list) {
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
	
	
	

	public void addBuffer2f(int attrib, ArrayList<Vector2f> list) {
		addBuffer2f(attrib,Utils.convertVec2fToArray(list));
	}
	public void addBuffer2f(int attrib,float[]data) {
		glBindVertexArray(id);
		int vbo=glGenBuffers();
		buffers.put(attrib, vbo);
		
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		glBufferData(GL_ARRAY_BUFFER,data,GL_STATIC_DRAW);
		glVertexAttribPointer(attrib, 2, GL_FLOAT, false, 2*Float.BYTES, 0);
		glEnableVertexAttribArray(attrib);
		glBindBuffer(GL_ARRAY_BUFFER,0);
		
		glBindVertexArray(0);
	}
	
	public void setBuffer2f(int attrib, ArrayList<Vector2f> list) {
		setBuffer2f(attrib,Utils.convertVec2fToArray(list));
	}
	public void setBuffer2f(int attrib,float[]data) {
		glBindVertexArray(id);
		
		int vbo=buffers.get(attrib);
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		glBufferData(GL_ARRAY_BUFFER,data,GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER,0);
		
		glBindVertexArray(0);
	}
	
	public void addBuffer1f(int attrib,ArrayList<Float>list) {
		addBuffer1f(attrib,Utils.convertFloatListToArray(list));
	}
	public void addBuffer1f(int attrib,float[]data) {
		glBindVertexArray(id);
		int vbo=glGenBuffers();
		buffers.put(attrib, vbo);
		
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		glBufferData(GL_ARRAY_BUFFER,data,GL_STATIC_DRAW);
		glVertexAttribPointer(attrib, 1, GL_FLOAT, false, 1*Float.BYTES, 0);
		glEnableVertexAttribArray(attrib);
		glBindBuffer(GL_ARRAY_BUFFER,0);
		
		glBindVertexArray(0);
	}
	
	public void setBuffer1f(int attrib, ArrayList<Float> list) {
		setBuffer2f(attrib,Utils.convertFloatListToArray(list));
	}
	public void setBuffer1f(int attrib,float[]data) {
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
	
	public void renderLines() {
		glBindVertexArray(id);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ebo);
		glDrawElements(GL_LINES,eboSize,GL_UNSIGNED_INT,0);
	}
	

}
