#version 400 core

in vec3 color;
in vec2 uvCoord;

uniform  sampler2D textureImage;

out vec4 out_color;


void main()
{
  out_color = texture(textureImage,uvCoord);
}