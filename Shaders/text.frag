#version 410 core

in vec2 uv;
in vec2 posInWindow;

out vec4 fragColor;

uniform sampler2D tex;
uniform sampler2D bg;

vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

vec3 rgb2hsv(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));
    
    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

void main(){
    fragColor=vec4(uv,0.0,1.0);
    vec4 fontColor=texture(tex,uv);
//    fragColor=fontColor;
    vec4 bgColor=texture(bg,posInWindow*0.5+0.5);
    
    float font=fontColor.x;
    vec3 bg=bgColor.xyz;
    
    
    vec3 color=bg;
    if(font<0.5){
        color=1.0-color;
    }else{
        discard;
    }
    
    
    fragColor=vec4(color,1);
//    fragColor=vec4(posInWindow*.5+.5,0,1);
}
