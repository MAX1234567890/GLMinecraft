package co.megadodo.mc.gl;

import static org.lwjgl.opengl.GL45.*;

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

}
