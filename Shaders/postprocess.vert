#version 410 core

layout (location=0) in vec2 inPos;

out vec2 uv;

void main(){
    gl_Position=vec4(inPos*2.0-1.0,0.0,1.0);
    uv=inPos;
}
