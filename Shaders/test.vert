#version 410 core

layout (location=0) in vec3 inPos;
layout (location=1) in vec3 inCol;

uniform mat4 perspective;
uniform mat4 view;

out vec3 color;

void main(){
    gl_Position=perspective*view*vec4(inPos,1.0);
    color=inCol;
}
