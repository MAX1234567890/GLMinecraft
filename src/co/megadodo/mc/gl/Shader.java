package co.megadodo.mc.gl;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import co.megadodo.mc.Utils;

public class Shader {

	public int id;

	/**
	 * Shaders/{{name}}.frag
	 * Shaders/{{name}}.vert
	 */
	public Shader(String name) {
		int vert=glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vert,Utils.loadFile("Shaders/"+name+".vert"));
		glCompileShader(vert);
		if(glGetShaderi(vert, GL_COMPILE_STATUS)==GL_FALSE) {
			System.err.println(glGetShaderInfoLog(vert));
			System.exit(1);
		}
		
		int frag=glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(frag,Utils.loadFile("Shaders/"+name+".frag"));
		glCompileShader(frag);
		if(glGetShaderi(frag, GL_COMPILE_STATUS)==GL_FALSE) {
			System.err.println(glGetShaderInfoLog(frag));
			System.exit(1);
		}
		
		id=glCreateProgram();
		glAttachShader(id,vert);
		glAttachShader(id,frag);
		glLinkProgram(id);
		
		if(glGetProgrami(id, GL_LINK_STATUS)==GL_FALSE) {
			System.err.println(glGetProgramInfoLog(id));
			System.exit(1);
		}		
	}
	
	public void bind() {
		glUseProgram(id);
	}
	
	public void setMat4f(String name,Matrix4f m) {
		int loc=glGetUniformLocation(id,name);
		FloatBuffer buf=BufferUtils.createFloatBuffer(16);
		buf.put(m.get(new float[16]));
		buf.flip();
		glUniformMatrix4fv(loc,false,buf);
	}
	
	public void set1i(String name,int i) {
		glUniform1i(glGetUniformLocation(id, name), i);
	}
	
	public void set1f(String name,float f) {
		glUniform1f(glGetUniformLocation(id, name), f);
	}
	
	public void setSampler(String name,int unit) {
		set1i(name,unit);
	}

}
