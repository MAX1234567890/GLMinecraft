package co.megadodo.mc.gl;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL45.*;

import java.nio.ByteBuffer;

public class Framebuffer implements Viewport {
	
	public int id;
	public int color;
	public float width;
	public float height;
	
	public float getX() {
		return 0;
	}
	
	public float getY() {
		return 0;
	}
	
	public float getW() {
		return width;
	}
	
	public float getH() {
		return height;
	}
	
	public Framebuffer() {
		width=1000;
		height=1000;
		color=glGenTextures();
		glBindTexture(GL_TEXTURE_2D,color);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA8,(int)width,(int)height,0,GL_BGRA,GL_UNSIGNED_BYTE,(ByteBuffer)null);
		
		
		id=glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER,id);
		
		glFramebufferTexture2D(GL_FRAMEBUFFER,GL_COLOR_ATTACHMENT0,GL_TEXTURE_2D,color,0);
		
		int rbo=glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, rbo);
		glRenderbufferStorage(GL_RENDERBUFFER,GL_DEPTH_COMPONENT24,(int)width,(int)height);
		
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rbo);
		
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER)!=GL_FRAMEBUFFER_COMPLETE) {
			throw new RuntimeException("Cannot complete framebuffer.");
		}
		
		glBindFramebuffer(GL_FRAMEBUFFER,0);
		
		
	}

	public void bindColor(int unit) {
		glActiveTexture(GL_TEXTURE0+unit);
		glBindTexture(GL_TEXTURE_2D,color);
	}

	
	public void start() {
		glBindFramebuffer(GL_FRAMEBUFFER,id);
	}
	
	public void end() {
		glBindFramebuffer(GL_FRAMEBUFFER,0);
	}
	

}
