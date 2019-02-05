package co.megadodo.mc;

import org.joml.Vector2f;
import org.joml.Vector3f;

import co.megadodo.mc.gl.Framebuffer;
import co.megadodo.mc.gl.Mesh;
import co.megadodo.mc.gl.Shader;
import co.megadodo.mc.gl.Text;
import co.megadodo.mc.gl.Window;

public class Minecraft {

	public static void main(String[] args) {
		Window window=new Window();
		
		Utils.printGL(true);
		
		Game game=new Game();
		
		game.init(window);

		while (window.shouldContinue()) {
			
			window.startFrame();
			Utils.clearGLError();
			
			game.render(window);

			
			game.update(window);
			
			
			Utils.printGLError();

			window.endFrame();
		}
	}

}