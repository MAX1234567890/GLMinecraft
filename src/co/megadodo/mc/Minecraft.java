package co.megadodo.mc;

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