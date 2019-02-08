package co.megadodo.mc;

public class Mathf {

	public static float lerp(double a,double b,double t) {
		return (float)(a+(b-a)*t);
	}
	
	public static float norm(double a,double b,double t) {
		return (float)((t-a)/(b-a));
	}
	
	public static float map(double t,double s1,double e1,double s2,double e2) {
		return (float)(lerp(s2,e2,norm(s1,e1,t)));
	}
	
	public static float cos(double f) {
		return (float)Math.cos(f);
	}
	
	public static float sin(double f) {
		return (float)Math.sin(f);
	}
	
	public static float random() {
		return (float)Math.random();
	}
	
	public static int floor(double f) {
		return (int)Math.floor(f);
	}
	
	public static float abs(double f) {
		return (float)Math.abs(f);
	}
	
	public static float atan2(double y,double x) {
		return (float)Math.atan2(y, x);
	}
	
	public static final float PI=3.1415926536f;
	
}
