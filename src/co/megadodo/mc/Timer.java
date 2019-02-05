package co.megadodo.mc;

public class Timer {
	
	public long lastMark=-1;
	
	public float delta() {
		if(lastMark==-1)return 0;
		return (System.currentTimeMillis()-lastMark)/1000.0f;
	}
	
	public void mark() {
		lastMark=System.currentTimeMillis();
	}
	
	public void shift(float t) {
		lastMark+=(long)(1000*t);
	}
	
	public Timer() {
		lastMark=-1;
	}


}
