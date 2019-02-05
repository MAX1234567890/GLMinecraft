package co.megadodo.mc;

public class FPSTimer {
	
	public static final int NUM_FRAMES=100;

	public int index;
	public float[] times;
	
	public Timer timer;
	
	public FPSTimer() {
		timer=new Timer();
		timer.mark();
		
		times=new float[NUM_FRAMES];
		for(int i=0;i<NUM_FRAMES;i++) {
			times[i]=1.0f/60.0f;
		}
	}
	
	public void countFrame() {
		for(int i=0;i<NUM_FRAMES-1;i++) {
			times[i]=times[i+1];
		}

		
		times[NUM_FRAMES-1]=timer.delta();
		timer.mark();
	}
	
	public float getSPF() {
		return sum()/NUM_FRAMES;
	}
	
	public float getFPS() {
		return NUM_FRAMES/sum();
	}
	
	public float sum() {
		float s=0;
		for(float f:times)s+=f;
		return s;
	}
	
}
