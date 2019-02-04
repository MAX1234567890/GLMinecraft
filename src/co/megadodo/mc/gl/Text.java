package co.megadodo.mc.gl;

import java.util.ArrayList;

import org.joml.Vector2f;

public class Text {

	public Shader shader;
	public Mesh mesh;
	public Texture tex;
	
	public Text() {
		shader=new Shader("text");
		
		mesh=new Mesh();
		mesh.addBuffer2f(0, new float[] {});
		mesh.addBuffer2f(1, new float[] {});
		mesh.setIndices(new int[] {});
		
		tex = new Texture("font.png");
	}
	
	public void setText(String text) {
		ArrayList<Vector2f>pos=new ArrayList<Vector2f>();
		ArrayList<Vector2f>uv=new ArrayList<Vector2f>();
		ArrayList<Integer>inds=new ArrayList<Integer>();
		int x=-1;
		int y=-1;
		char[]arr=text.toCharArray();
		for(char c:arr) {
			if(c==' ') {
				x++;
				continue;
			}
			if(c=='\n') {
				y--;
				x=-1;
				continue;
			}
			AtlasPos p=Font.getPos(c);
			float fx=p.x;
			float fy=p.y;
			float w=p.w;
			float h=p.h;
			x++;
			int n=pos.size();
			pos.add(new Vector2f(x,y));
			pos.add(new Vector2f(x+1,y));
			pos.add(new Vector2f(x,y+1));
			pos.add(new Vector2f(x+1,y+1));
			uv.add(new Vector2f(fx,fy));
			uv.add(new Vector2f(fx+w,fy));
			uv.add(new Vector2f(fx,fy+h));
			uv.add(new Vector2f(fx+w,fy+h));
			inds.add(n+0);
			inds.add(n+1);
			inds.add(n+2);
			inds.add(n+1);
			inds.add(n+2);
			inds.add(n+3);
		}
		mesh.setBuffer2f(0, pos);
		mesh.setBuffer2f(1, uv);
		mesh.setIndices(inds);
	}
	
	public void render(float x,float y,float s) {
		shader.bind();
		
		shader.set1f("aspect", Font.getAspectRatio());
		shader.set1f("posx", x);
		shader.set1f("posy", y);
		shader.set1f("size", s);
		
		tex.bind(0);
		shader.setSampler("tex", 0);
		
		mesh.renderElements();
	}
	
}
