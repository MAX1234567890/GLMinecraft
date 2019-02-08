package co.megadodo.mc;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import co.megadodo.mc.block.Block;
import co.megadodo.mc.gl.Framebuffer;
import co.megadodo.mc.gl.Mesh;
import co.megadodo.mc.gl.MouseMoveCallback;
import co.megadodo.mc.gl.Shader;
import co.megadodo.mc.gl.Text;
import co.megadodo.mc.gl.Window;

public class Game {

	public Mesh quad;
	public Shader postprocess;
	public Text txt;
	public Entity player;
	public Framebuffer fboWorld;
	public FPSTimer fps;
	public Timer frameTimer;
	public float dt;
	public ChunkManager chunkManager;

	public Intersection intersection;
	public SelectedBlockRenderer selectedBlockRenderer;
	
	public boolean inOverlay;
	
	public void init(Window window) {
		inOverlay=false;
		chunkManager=new ChunkManager();
		
		player=new Entity(new Vector3f(0,50,0), new Vector3f(0.25f,1f,0.25f), true);

		
		quad=new Mesh();
		quad.addBuffer2f(0, new float[] {
				0,0,
				1,0,
				0,1,
				1,1
		});
		quad.setIndices(new int[] {
				0,1,2,
				1,2,3
		});
		
		postprocess=new Shader("postprocess");
		
		selectedBlockRenderer=new SelectedBlockRenderer();

		
		fboWorld=new Framebuffer();

		txt=new Text(fboWorld);
		
		fps=new FPSTimer();
		
		frameTimer=new Timer();
		frameTimer.mark();
		
		window.hideMouse();
		
		window.addOnMouseMove(new MouseMoveCallback() {
			
			@Override
			public void onMouseMove(Window window, float mx, float my) {
				if(inOverlay)return;
				player.cam.handleMouseMove(new Vector2f(mx-window.width/2,my-window.height/2));
//				window.setMouse(window.width/2, window.height/2);
			}
		});
	}
	
	public void update(Window window) {
		intersection=chunkManager.intersectWorld(player.cam.pos, player.cam.dir, 100);
		fps.countFrame();
		
		dt=frameTimer.delta();
		player.dt=dt;
		frameTimer.mark();
		
		if(!inOverlay)player.handleInput(window,chunkManager);
		player.update(chunkManager);
		

		if(inOverlay)window.releaseMouse();
		else window.hideMouse();
		
		if(window.mouseLeftJustPressed&&intersection!=null) {
			chunkManager.setAndRemeshBlock(intersection.hit,null);
		}
		if(window.mouseRightJustPressed&&intersection!=null) {
			chunkManager.setAndRemeshBlock(intersection.prev,Block.stone);
		}
		
		if(window.wasKeyJustPressed('C')&&intersection!=null) {
			int o=1;
			for(int x=-o;x<=o;x++) {
				for(int y=-o;y<=o;y++) {
					for(int z=-o;z<=o;z++) {
						chunkManager.setAndRemeshBlock(new Vector3i(intersection.hit).add(new Vector3i(x,y,z)), null);
					}
				}
			}
		}

		
		if(window.isKeyDown('/'))window.close();
		
		if(window.wasKeyJustPressed(Window.ESCAPE)) {
			inOverlay=!inOverlay;
		}
		
	}
	
	public void render(Window window) {			
		fboWorld.start();
		Utils.initGLFrame(fboWorld);
		Utils.setDepth(true);
		
		chunkManager.render(player.cam);
		chunkManager.update(player.cam);
		
		if(intersection!=null) {
//			Utils.setDepth(false);
			selectedBlockRenderer.render(player.cam, intersection.hit.x, intersection.hit.y, intersection.hit.z);
//			Utils.setDepth(true);
		}
		
		fboWorld.end();
		
		
		Utils.initGLFrame(window);
		Utils.setDepth(false);

		postprocess.bind();
		postprocess.setBool("overlay",inOverlay);
		fboWorld.bindColor(0);
		postprocess.setSampler("tex", 0);
		quad.renderElements();
		
		

		txt.setText("Minecraft\n"
				 + "Camera position: ["+Utils.formatFloat(player.cam.pos.x)+", "+Utils.formatFloat(player.cam.pos.y)+", "+Utils.formatFloat(player.cam.pos.z)+"]\n"
				 + "Camera direction: ["+Utils.formatFloat(player.cam.dir.x)+", "+Utils.formatFloat(player.cam.dir.y)+", "+Utils.formatFloat(player.cam.dir.z)+"]\n"
				 + "FPS: "+Utils.formatFloat(fps.getFPS())+"\n"
				 + "SPF: "+Utils.formatFloat(fps.getSPF())+"\n"
				 + "DT: "+Utils.formatFloat(dt)+"\n"
				 + "\n"
				 + "Chunks in memory: "+chunkManager.chunks.size()+"\n"
				 + "Chunks rendered: "+chunkManager.numRenderedChunks()+"\n"
				 + "Chunks to add: "+chunkManager.chunksToAdd.size()+"\n"
				 + "Chunks to rem: "+chunkManager.chunksToRem.size()+"\n"
				 + "Chunks to remesh: "+chunkManager.chunksToRemesh.size()+"\n");
		txt.render(-1+0.0025f,1-0.0025f, 0.03f);
		
		
		txt.setText("+");
		float s=0.05f;
		txt.render(0,0, s);
	}

}
