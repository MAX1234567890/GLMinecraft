#version 410 core

in vec2 uv;
in float ao;

uniform sampler2D tex;

out vec4 fragColor;

void main(){
    
    float aoFactor=clamp(ao*2.0,0.5,1.0);
    
    vec4 tc=texture(tex,uv);
    if(tc.w<0.5)discard;
    vec3 texColor=tc.xyz;
//    texColor/=max(texColor.x,max(texColor.y,texColor.z));
    vec3 color=texColor*aoFactor;
    
//    vec3 color=);
    
    fragColor=vec4(color,1);
}
