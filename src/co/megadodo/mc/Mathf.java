package co.megadodo.mc;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class Mathf {

	private static OpenSimplexNoise osn;
	private static float noiseScale;
	
	public static void setNoiseScale(float f) {
		noiseScale=f;
	}
	
	public static void setNoiseSeed(long seed) {
		osn=new OpenSimplexNoise(seed);
	}
	
	public static float noise(float x,float y) {
		return (float)osn.eval(noiseScale*x, noiseScale*y);
	}
	
	public static float noise(float x,float y,float z) {
		return (float)osn.eval(noiseScale*x, noiseScale*y, noiseScale*z);
	}
	
	public static float noise(float x,float y,float z,float w) {
		return (float)osn.eval(noiseScale*x, noiseScale*y, noiseScale*z, noiseScale*w);
	}
	
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
	
	public static float fract(float x) {
		return x%1;
	}
	
	public static Vector2f fract(Vector2f v) {
		return new Vector2f(fract(v.x),fract(v.y));
	}
	
	private static final float W0=42.14774866190041f;
	private static final float W1=33.425423425114296f;
	
	public static float hash2f(float fx,float fy) {
		Vector2f c=new Vector2f(5+abs(fx*0.1f),5+abs(fy*123-654));
		float x=c.x*fract(c.x*W0);
		float y=c.y*fract(c.y*W1);
		return fract(x*y);
	}
	
	public static final float PI=3.1415926536f;
	
}
