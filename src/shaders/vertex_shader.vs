#version 400 core

in vec3 position;
in vec2 textureCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;


out vec2 uvCoord; 

void main()
{
  gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position.xyz,1.0);
  uvCoord =textureCoords;
  
}