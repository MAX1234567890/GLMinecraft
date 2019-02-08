package co.megadodo.mc.ui;

import co.megadodo.mc.ChunkManager;
import co.megadodo.mc.gl.AtlasPos;
import co.megadodo.mc.gl.Mesh;
import co.megadodo.mc.gl.Shader;

public class BlockSprite {
	
	public Shader shader;
	public Mesh mesh;
	
	public BlockSprite() {
		shader=new Shader("block-sprite");
		mesh=new Mesh();
		mesh.addBuffer2f(0, new float[] {
				0,0,
				1,0,
				0,1,
				1,1
		});
		mesh.setIndices(new int[] {
				0,1,2,
				1,2,3
		});
	}
	
	public void render(AtlasPos sprite,float x,float y,float w,float h) {
		shader.bind();
		shader.set2f("pos", x, y);
		shader.set2f("size", w, h);
		shader.set2f("texPos", sprite.x, sprite.y);
		shader.set2f("texSize", sprite.w, sprite.h);
		ChunkManager.texture.bind(0);
		shader.setSampler("tex", 0);
		mesh.renderElements();
	}

}
