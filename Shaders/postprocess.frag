#version 410 core

in vec2 uv;

out vec4 fragColor;

uniform sampler2D tex;

void main(){
    fragColor=texture(tex,uv);
//    fragColor=vec4(uv,0,1);
}
