#version 410 core

layout (location=0) in vec3 inPos;

uniform vec3 offset;
uniform mat4 perspective;
uniform mat4 view;

uniform float scale;
//0,1 -> -scale,1+scale
void main(){
    gl_Position=perspective*view*vec4(offset+inPos*(2.0*scale+1.0)-scale,1.0);
}
