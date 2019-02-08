#version 410 core

uniform vec2 size;

in vec2 uv;

out vec4 fragColor;

uniform bool selected;

void main(){
    vec2 diff=abs(uv-vec2(0.5));
    if(diff.x>0.45||diff.y>0.45){
        fragColor=vec4(.25);
        if(selected)fragColor=vec4(.75);
    }else{
        fragColor=vec4(.5);
    }
}
