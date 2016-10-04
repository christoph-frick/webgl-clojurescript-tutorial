void main() {
	gl_Position = proj * view * model * vec4 (position, 1.0);
}
