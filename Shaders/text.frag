#version 410 core

in vec2 uv;
in vec2 posInWindow;

out vec4 fragColor;

uniform sampler2D tex;
uniform sampler2D bg;

void main(){
    fragColor=vec4(uv,0.0,1.0);
    vec4 fontColor=texture(tex,uv);
//    fragColor=fontColor;
    vec4 bgColor=texture(bg,posInWindow*0.5+0.5);
    if(fontColor.x<.5)bgColor=1.0-bgColor;

    fragColor=bgColor;
//    fragColor=vec4(posInWindow*.5+.5,0,1);
}
