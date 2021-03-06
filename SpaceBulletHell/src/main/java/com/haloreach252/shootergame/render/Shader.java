package com.haloreach252.shootergame.render;

import static org.lwjgl.opengl.GL20.*;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.FloatBuffer;

public class Shader {

	private int programObject;
	private int vertexShaderObject;
	private int fragmentShaderObject;
	
	public Shader(String filename) {
		programObject = glCreateProgram();
		
		vertexShaderObject = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShaderObject, readFile(filename + ".vshader"));
		glCompileShader(vertexShaderObject);
		if(glGetShaderi(vertexShaderObject, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(vertexShaderObject));
			System.exit(1);
		}
		
		fragmentShaderObject = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShaderObject, readFile(filename + ".fshader"));
		glCompileShader(fragmentShaderObject);
		if(glGetShaderi(fragmentShaderObject, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(fragmentShaderObject));
			System.exit(1);
		}
		
		glAttachShader(programObject, vertexShaderObject);
		glAttachShader(programObject, fragmentShaderObject);
		
		glBindAttribLocation(programObject, 0, "vertices");
		glBindAttribLocation(programObject, 1, "textures");
		
		glLinkProgram(programObject);
		if(glGetProgrami(programObject, GL_LINK_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(programObject));
			System.exit(1);
		}
		glValidateProgram(programObject);
		if(glGetProgrami(programObject, GL_VALIDATE_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(programObject));
			System.exit(1);
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		glDetachShader(programObject, vertexShaderObject);
		glDetachShader(programObject, fragmentShaderObject);
		glDeleteShader(vertexShaderObject);
		glDeleteShader(fragmentShaderObject);
		glDeleteProgram(programObject);
		super.finalize();
	}
	
	public void setUniform(String name, int value) {
		int location = glGetUniformLocation(programObject, name);
		if(location != -1) {
			glUniform1i(location, value);
		}
	}
	
	public void setUniform(String name, Vector4f value) {
		int location = glGetUniformLocation(programObject, name);
		if(location != -1) {
			glUniform4f(location, value.x, value.y, value.z, value.w);
		}
	}
	
	public void setUniform(String name, Matrix4f value) {
		int location = glGetUniformLocation(programObject, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		if(location != -1) {
			glUniformMatrix4fv(location, false, buffer);
		}
	}
	
	public void bind() {
		glUseProgram(programObject);
	}
	
	private String readFile(String filename) {
		StringBuilder string = new StringBuilder();
		BufferedReader br;
		try {
			URI filepath = getClass().getResource("/shaders/" + filename).toURI();
			br = new BufferedReader(new FileReader(new File(filepath)));
			String line;
			while((line = br.readLine()) != null) {
				string.append(line);
				string.append("\n");
			}
			br.close();
		} catch(IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return string.toString();
	}
	
}
