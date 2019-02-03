#version 410 core

layout (location=0) in vec2 inPos;
layout (location=1) in vec2 inUV;

uniform float aspect;
uniform float posx;
uniform float posy;
uniform float size;

out vec2 uv;

void main(){
    vec2 p=inPos;
    p.x*=aspect;
    p*=size;
    p+=vec2(posx,posy);
    gl_Position=vec4(p,0.0,1.0);
    uv=inUV;
}
