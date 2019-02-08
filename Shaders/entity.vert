#version 410 core

layout (location=0) in vec3 inPos;

uniform mat4 perspective;
uniform mat4 view;
uniform mat4 model;

uniform vec3 pos;
uniform vec3 size;

out vec3 offset;

void main(){
    gl_Position=perspective*view*vec4(pos+size*inPos,1);
    offset=inPos;
}
