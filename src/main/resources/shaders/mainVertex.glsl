#version 330 core

layout(location = 0) in vec3 position;

uniform mat4 projectionMatrix;
uniform mat4 worldMatrix;

out vec3 passColor;

void main() {
    gl_Position = projectionMatrix * worldMatrix * vec4(position, 1.0);
}