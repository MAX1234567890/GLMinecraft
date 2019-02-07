#version 410 core

in vec2 uv;

out vec4 fragColor;

uniform sampler2D tex;
uniform bool overlay;

void main(){
    fragColor=texture(tex,uv);
    if(overlay){
        fragColor*=clamp(1.0/(1.0+length(uv-vec2(0.5))),0.0,1.0);
    }
//    fragColor=vec4(uv,0,1);
}
