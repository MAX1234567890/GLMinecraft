package co.megadodo.mc;

public class Mathf {

	public static float lerp(float a,float b,float t) {
		return a+(b-a)*t;
	}
	
	public static float norm(float a,float b,float t) {
		return (t-a)/(b-a);
	}
	
	public static float map(float t,float s1,float e1,float s2,float e2) {
		return lerp(s2,e2,norm(s1,e1,t));
	}
	
	public static float cos(float f) {
		return (float)Math.cos(f);
	}
	
	public static float sin(float f) {
		return (float)Math.sin(f);
	}
	
	public static final float PI=3.1415926536f;
	
}
