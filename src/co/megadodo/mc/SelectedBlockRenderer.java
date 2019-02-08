package co.megadodo.mc;

import co.megadodo.mc.gl.Mesh;
import co.megadodo.mc.gl.Shader;

public class SelectedBlockRenderer {
	
	public Mesh mesh;
	public Shader shader;
	
	public SelectedBlockRenderer() {
		shader=new Shader("selected-block");
		mesh=new Mesh();
		mesh.addBuffer3f(0, new float[] {
				0,0,0,
				1,0,0,
				1,1,0,
				0,1,0,

				0,0,1,
				1,0,1,
				1,1,1,
				0,1,1,

				0,0,0,
				0,1,0,
				0,1,1,
				0,0,1,

				1,0,0,
				1,1,0,
				1,1,1,
				1,0,1,

				0,0,0,
				1,0,0,
				1,0,1,
				0,0,1,

				0,1,0,
				1,1,0,
				1,1,1,
				0,1,1
		});
		mesh.setIndices(new int[] {
				0 ,1 ,    1 ,2 ,    2 ,3 ,    3 ,0 ,
				4 ,5 ,    5 ,6 ,    6 ,7 ,    7 ,4 ,
				8 ,9 ,    9 ,10,    10,11,    11,8 ,
				12,13,    13,14,    14,15,    15,12,
				16,17,    17,18,    18,19,    19,16,
				20,21,    21,22,    22,23,    23,20
		});
	}
	
	public void render(Camera cam,int x,int y,int z) {
		
		Utils.setLineSmooth(true);
//		Utils.setLineWidth(40);
		
		shader.bind();
		
		shader.set3f("offset",x,y,z);
		shader.setMat4f("perspective", cam.getPerspective());
		shader.setMat4f("view", cam.getView());
		
		shader.set1f("scale",0.01f);
		
		mesh.renderLines();
	}

}
