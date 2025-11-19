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
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glGetUniformi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import com.whyx.lwjgltest.engine.utils.FileUtils;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

/**
 * @author Samuel Wykes. Responsible for GLSL shading.
 */
public class Shader {

  private final String vertexShader, fragmentShader;
  private int vertexId, fragmentId, programId;

  private final Map<String, Integer> uniformLocations;

  private final List<String> uniforms;

  public Shader(final String vertexPath, final String fragmentPath, final List<String> uniforms) {
    this.vertexShader = FileUtils.loadAsString(vertexPath);
    this.fragmentShader = FileUtils.loadAsString(fragmentPath);
    this.uniformLocations = new HashMap<>();
    this.uniforms = uniforms;
  }

  public void init() throws Exception {
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

    if (glGetShaderi(this.fragmentId, GL_COMPILE_STATUS) == GL_FALSE) {
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

    for (final String uniform : this.uniforms) {
      this.createUniform(uniform);
    }
  }

  public void bind() {
    glUseProgram(this.programId);
  }

  public void unbind() {
    glUseProgram(0);
  }

  public void createUniform(final String uniformName) throws Exception {
    final int uniformLocation = glGetUniformLocation(this.programId, uniformName);
    if (uniformLocation < 0) {
      throw new Exception("Could not find uniform: " + uniformName);
    }
    this.uniformLocations.put(uniformName, uniformLocation);
  }

  public void setUniformMatrix4f(final String uniformName, final Matrix4f value) {
    try (final MemoryStack stack = MemoryStack.stackPush()) {
      final FloatBuffer fb = stack.mallocFloat(16);
      value.get(fb);
      glUniformMatrix4fv(this.uniformLocations.get(uniformName), false, fb);
    }
  }

  public void setUniformInt(final String uniformName, final int value) {
    glGetUniformi(this.uniformLocations.get(uniformName), value);
  }

  public void dispose() {
    glDeleteProgram(this.programId);
  }
}
