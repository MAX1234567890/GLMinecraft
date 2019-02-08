package co.megadodo.mc.ui;

import co.megadodo.mc.gl.Mesh;
import co.megadodo.mc.gl.Shader;

public class BorderedPanel {

	public Mesh mesh;
	public Shader shader;
	
	public BorderedPanel() {
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
		shader=new Shader("bordered-panel");
	}
	
	public void render(boolean selected,float x,float y,float w,float h) {
		shader.bind();
		shader.set2f("pos",x,y);
		shader.set2f("size", w, h);
		shader.set1b("selected", selected);
		mesh.renderElements();
	}
	
}
