#version 410 core

layout (location=0) in vec3 inPos;

uniform mat4 perspective;
uniform mat4 view;

uniform vec3 offset;
uniform vec3 scale;

void main(){
    gl_Position=perspective*view*vec4(offset+scale*inPos,1);
}
