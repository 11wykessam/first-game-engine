package com.whyx.lwjgltest.engine.io.graphics.shader;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import com.whyx.lwjgltest.engine.utils.FileUtils;

/**
 * @author Samuel Wykes.
 * Responsible for GLSL shading.
 */
public class Shader {

  private final String vertexShader, fragmentShader;
  private int vertexId, fragmentId, programId;

  public Shader(final String vertexPath, final String fragmentPath) {
    this.vertexShader = FileUtils.loadAsString(vertexPath);
    this.fragmentShader = FileUtils.loadAsString(fragmentPath);
  }

  public void init() {
    this.programId = glCreateProgram();
    this.vertexId = glCreateShader(GL_VERTEX_SHADER);

    glShaderSource(this.vertexId, this.vertexShader);
    glCompileShader(this.vertexId);

    if (glGetShaderi(this.vertexId, GL_COMPILE_STATUS) == GL_FALSE) {
      System.err.println("Vertex Shader Failed to Compile!");
      System.err.println(glGetShaderInfoLog(this.vertexId));
      System.exit(-1);
    }

    this.fragmentId = glCreateShader(GL_FRAGMENT_SHADER);

    glShaderSource(this.fragmentId, this.fragmentShader);
    glCompileShader(this.fragmentId);

    if(glGetShaderi(this.fragmentId, GL_COMPILE_STATUS) == GL_FALSE) {
      System.err.println("Fragment Shader Failed to Compile!");
      System.err.println(glGetShaderInfoLog(this.fragmentId));
      System.exit(-1);
    }

    glAttachShader(this.programId, this.vertexId);
    glAttachShader(this.programId, this.fragmentId);

    glLinkProgram(this.programId);
    if (glGetProgrami(this.programId, GL_LINK_STATUS) == GL_FALSE) {
      System.err.println("Shader Program Failed to Link!");
      System.err.println(glGetProgramInfoLog(this.programId));
      System.exit(-1);
    }

    glValidateProgram(this.programId);
    if (glGetProgrami(this.programId, GL_VALIDATE_STATUS) == GL_FALSE) {
      System.err.println("Shader Program Failed to Link!");
      System.err.println(glGetProgramInfoLog(this.programId));
      System.exit(-1);
    }

    glDeleteShader(this.vertexId);
    glDeleteShader(this.fragmentId);
  }

  public void bind() {
    glUseProgram(this.programId);
  }

  public void unbind() {
    glUseProgram(0);
  }

  public void dispose() {
    glDeleteProgram(this.programId);
  }
}
