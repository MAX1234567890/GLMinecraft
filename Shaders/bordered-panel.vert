#version 410 core

layout (location=0) in vec2 inPos;

uniform vec2 pos;
uniform vec2 size;

out vec2 uv;

void main(){
    gl_Position=vec4(pos+inPos*size,0,1);
    uv=inPos;
}
