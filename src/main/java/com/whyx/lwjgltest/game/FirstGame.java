package com.whyx.lwjgltest.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.opengl.GL33.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL33.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL33.glClear;
import static org.lwjgl.opengl.GL33.glClearColor;

import com.whyx.lwjgltest.engine.io.IGameLogic;
import com.whyx.lwjgltest.engine.io.graphics.mesh.Mesh;
import com.whyx.lwjgltest.engine.io.graphics.mesh.SimpleMesh;
import com.whyx.lwjgltest.engine.io.graphics.renderer.Renderer;
import com.whyx.lwjgltest.engine.io.graphics.shader.Shader;
import com.whyx.lwjgltest.engine.io.graphics.vertex.Vertex;
import com.whyx.lwjgltest.engine.io.graphics.window.IWindow;
import java.util.List;
import org.joml.Vector3f;

/**
 * @author Samuel Wykes.
 * Dummy game used to test the engine.
 */
public class FirstGame implements IGameLogic {

  private Mesh firstMesh;

  private Shader shader;

  private Renderer renderer;

  @Override
  public void init() {
    this.shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
    this.renderer = new Renderer(this.shader);
    this.firstMesh = SimpleMesh.builder()
        .vertices(List.of(
            Vertex.builder()
                .position(new Vector3f(-0.5f, 0.0f, 0.0f))
                .build(),
            Vertex.builder()
                .position(new Vector3f(0f, 0.5f, 0.0f))
                .build(),
            Vertex.builder()
                .position(new Vector3f(0.5f, 0.0f, 0.0f))
                .build()
        ))
        .indices(List.of(0, 1, 2))
        .renderer(this.renderer)
        .build();
    this.shader.init();
  }

  @Override
  public void input(final IWindow window) {

  }

  @Override
  public void update(final IWindow window, final float interval) {
    if (window.getKeyboardCallbacks().isKeyDown(GLFW_KEY_ESCAPE) && !window.windowShouldClose()) {
      window.close();
    }
    if (window.getKeyboardCallbacks().isKeyDown(GLFW_KEY_F11) && !window.isFullscreen()) {
      window.setFullscreen(!window.isFullscreen());
    }
  }

  @Override
  public void render(final IWindow window) {
    glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    this.firstMesh.render(window);
  }

  @Override
  public void cleanup() {
    this.shader.dispose();
  }
}
