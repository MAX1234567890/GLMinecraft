package co.megadodo.mc.gl;

public class Font {
	
	public static AtlasPos getPos(char c) {
		int w=20;
		int h=6;
		if(c=='A')return new AtlasPos(0,5, w,h);
		if(c=='B')return new AtlasPos(1,5, w,h);
		if(c=='C')return new AtlasPos(2,5, w,h);
		if(c=='D')return new AtlasPos(3,5, w,h);
		if(c=='E')return new AtlasPos(4,5, w,h);
		if(c=='F')return new AtlasPos(5,5, w,h);
		if(c=='G')return new AtlasPos(6,5, w,h);
		if(c=='H')return new AtlasPos(7,5, w,h);
		if(c=='I')return new AtlasPos(8,5, w,h);
		if(c=='J')return new AtlasPos(9,5, w,h);
		if(c=='K')return new AtlasPos(10,5, w,h);
		if(c=='L')return new AtlasPos(11,5, w,h);
		if(c=='M')return new AtlasPos(12,5, w,h);
		if(c=='N')return new AtlasPos(13,5, w,h);
		if(c=='O')return new AtlasPos(14,5, w,h);
		if(c=='P')return new AtlasPos(15,5, w,h);
		if(c=='Q')return new AtlasPos(16,5, w,h);
		if(c=='R')return new AtlasPos(17,5, w,h);
		if(c=='S')return new AtlasPos(18,5, w,h);
		if(c=='T')return new AtlasPos(19,5, w,h);
		if(c=='U')return new AtlasPos(0,4, w,h);
		if(c=='V')return new AtlasPos(1,4, w,h);
		if(c=='W')return new AtlasPos(2,4, w,h);
		if(c=='X')return new AtlasPos(3,4, w,h);
		if(c=='Y')return new AtlasPos(4,4, w,h);
		if(c=='Z')return new AtlasPos(5,4, w,h);
		

		if(c=='a')return new AtlasPos(0,3, w,h);
		if(c=='b')return new AtlasPos(1,3, w,h);
		if(c=='c')return new AtlasPos(2,3, w,h);
		if(c=='d')return new AtlasPos(3,3, w,h);
		if(c=='e')return new AtlasPos(4,3, w,h);
		if(c=='f')return new AtlasPos(5,3, w,h);
		if(c=='g')return new AtlasPos(6,3, w,h);
		if(c=='h')return new AtlasPos(7,3, w,h);
		if(c=='i')return new AtlasPos(8,3, w,h);
		if(c=='j')return new AtlasPos(9,3, w,h);
		if(c=='k')return new AtlasPos(10,3, w,h);
		if(c=='l')return new AtlasPos(11,3, w,h);
		if(c=='m')return new AtlasPos(12,3, w,h);
		if(c=='n')return new AtlasPos(13,3, w,h);
		if(c=='o')return new AtlasPos(14,3, w,h);
		if(c=='p')return new AtlasPos(15,3, w,h);
		if(c=='q')return new AtlasPos(16,3, w,h);
		if(c=='r')return new AtlasPos(17,3, w,h);
		if(c=='s')return new AtlasPos(18,3, w,h);
		if(c=='t')return new AtlasPos(19,3, w,h);
		
		if(c=='u')return new AtlasPos(0,2, w,h);
		if(c=='v')return new AtlasPos(1,2, w,h);
		if(c=='w')return new AtlasPos(2,2, w,h);
		if(c=='x')return new AtlasPos(3,2, w,h);
		if(c=='y')return new AtlasPos(4,2, w,h);
		if(c=='z')return new AtlasPos(5,2, w,h);
		if(c=='0')return new AtlasPos(6,2, w,h);
		if(c=='1')return new AtlasPos(7,2, w,h);
		if(c=='2')return new AtlasPos(8,2, w,h);
		if(c=='3')return new AtlasPos(9,2, w,h);
		if(c=='4')return new AtlasPos(10,2, w,h);
		if(c=='5')return new AtlasPos(11,2, w,h);
		if(c=='6')return new AtlasPos(12,2, w,h);
		if(c=='7')return new AtlasPos(13,2, w,h);
		if(c=='8')return new AtlasPos(14,2, w,h);
		if(c=='9')return new AtlasPos(15,2, w,h);
		
		if(c=='~')return new AtlasPos(0,1, w,h);
		if(c=='`')return new AtlasPos(1,1, w,h);
		if(c=='!')return new AtlasPos(2,1, w,h);
		if(c=='@')return new AtlasPos(3,1, w,h);
		if(c=='#')return new AtlasPos(4,1, w,h);
		if(c=='$')return new AtlasPos(5,1, w,h);
		if(c=='%')return new AtlasPos(6,1, w,h);
		if(c=='^')return new AtlasPos(7,1, w,h);
		if(c=='&')return new AtlasPos(8,1, w,h);
		if(c=='*')return new AtlasPos(9,1, w,h);
		if(c=='(')return new AtlasPos(10,1, w,h);
		if(c==')')return new AtlasPos(11,1, w,h);
		if(c=='_')return new AtlasPos(12,1, w,h);
		if(c=='+')return new AtlasPos(13,1, w,h);
		if(c=='-')return new AtlasPos(14,1, w,h);
		if(c=='=')return new AtlasPos(15,1, w,h);
		if(c=='{')return new AtlasPos(16,1, w,h);
		if(c=='}')return new AtlasPos(17,1, w,h);
		if(c=='|')return new AtlasPos(18,1, w,h);
		if(c=='[')return new AtlasPos(19,1, w,h);
		if(c==']')return new AtlasPos(0,0, w,h);
		if(c=='\\')return new AtlasPos(1,0, w,h);
		if(c==':')return new AtlasPos(2,0, w,h);
		if(c=='\"')return new AtlasPos(3,0, w,h);
		if(c==';')return new AtlasPos(4,0, w,h);
		if(c=='\'')return new AtlasPos(5,0, w,h);
		if(c=='<')return new AtlasPos(6,0, w,h);
		if(c=='>')return new AtlasPos(7,0, w,h);
		if(c=='?')return new AtlasPos(8,0, w,h);
		if(c==',')return new AtlasPos(9,0, w,h);
		if(c=='.')return new AtlasPos(10,0, w,h);
		if(c=='/')return new AtlasPos(11,0, w,h);
		
		if(c==' ')return new AtlasPos(w-2,h-1, w,h);
		return new AtlasPos(w-1,h-1,w,h);
	}
	
	public static float getAspectRatio() {
		return 0.6f;
	}
}
