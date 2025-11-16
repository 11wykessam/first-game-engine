package com.whyx.lwjgltest.game.colourTest;

import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.PROJECTION_MATRIX_UNIFORM;
import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.WORLD_MATRIX_UNIFORM;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.opengl.GL33.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL33.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL33.glClear;
import static org.lwjgl.opengl.GL33.glClearColor;

import com.whyx.lwjgltest.engine.io.IGameLogic;
import com.whyx.lwjgltest.engine.io.graphics.camera.Camera;
import com.whyx.lwjgltest.engine.io.graphics.camera.ICamera;
import com.whyx.lwjgltest.engine.io.graphics.entity.GameEntity;
import com.whyx.lwjgltest.engine.io.graphics.entity.IGameEntity;
import com.whyx.lwjgltest.engine.io.graphics.mesh.Mesh;
import com.whyx.lwjgltest.engine.io.graphics.renderer.Renderer;
import com.whyx.lwjgltest.engine.io.graphics.shader.Shader;
import com.whyx.lwjgltest.engine.io.graphics.vertex.Vertex;
import com.whyx.lwjgltest.engine.io.graphics.window.IWindow;
import java.util.List;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * @author Samuel Wykes. Dummy game used to test the engine.
 */
public class ColourGame implements IGameLogic {

  long arg = 1;

  private IGameEntity entity;

  private Shader shader;

  private Renderer renderer;

  private ICamera camera;

  @Override
  public void init() throws Exception {
    this.shader = new Shader(
        "/shaders/mainVertex.glsl",
        "/shaders/mainFragment.glsl",
          List.of(PROJECTION_MATRIX_UNIFORM, WORLD_MATRIX_UNIFORM)
        );
    this.renderer = Renderer.builder()
        .shader(this.shader)
        .build();

    final float size = 0.5f;

    final List<Vertex> vertices = List.of(
        // Front face vertices (z = size)
        Vertex.builder()
            .position(new Vector3f(-size, size, size))
            .colour(new Vector4f(1.0f, 0.0f, 0.0f, 1.0f))
            .build(),    // 0: front top left
        Vertex.builder()
            .position(new Vector3f(size, size, size))
            .colour(new Vector4f(0.0f, 1.0f, 0.0f, 1.0f))
            .build(),     // 1: front top right
        Vertex.builder()
            .position(new Vector3f(size, -size, size))
            .colour(new Vector4f(0.0f, 0.0f, 1.0f, 1.0f))
            .build(),
        // 2: front bottom right
        Vertex.builder()
            .position(new Vector3f(-size, -size, size))
            .colour(new Vector4f(1.0f, 1.0f, 0.0f, 1.0f))
            .build(),
        // 3: front bottom left

        // Back face vertices (z = -size)
        Vertex.builder()
            .position(new Vector3f(-size, size, -size))
            .colour(new Vector4f(1.0f, 0.0f, 0.0f, 1.0f))
            .build(),   // 4: back top left
        Vertex.builder()
            .position(new Vector3f(size, size, -size))
            .colour(new Vector4f(0.0f, 1.0f, 0.0f, 1.0f))
            .build(),    // 5: back top right
        Vertex.builder()
            .position(new Vector3f(size, -size, -size))
            .colour(new Vector4f(0.0f, 0.0f, 1.0f, 1.0f))
            .build(),
        // 6: back bottom right
        Vertex.builder()
            .position(new Vector3f(-size, -size, -size))
            .colour(new Vector4f(1.0f, 1.0f, 0.0f, 1.0f))
            .build()
        // 7: back bottom left
    );

    // Define indices for all faces (counter-clockwise)
    final List<Integer> indices = List.of(
        // Front face
        0, 1, 3,    // First triangle
        3, 1, 2,    // Second triangle

        // Right face
        1, 5, 2,    // First triangle
        2, 5, 6,    // Second triangle

        // Back face
        5, 4, 6,    // First triangle
        6, 4, 7,    // Second triangle

        // Left face
        4, 0, 7,    // First triangle
        7, 0, 3,    // Second triangle

        // Top face
        4, 5, 0,    // First triangle
        0, 5, 1,    // Second triangle

        // Bottom face
        3, 2, 7,    // First triangle
        7, 2, 6     // Second triangle
    );

    final Mesh firstMesh = Mesh.builder()
        .vertices(vertices)
        .indices(indices)
        .build();

    this.entity = GameEntity.builder()
        .mesh(firstMesh)
        .scale(1f)
        .rotation(new Vector3f(0.0f, 0.0f, 0.0f))
        .position(new Vector3f(0.0f, 0.0f, -2.0f))
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

    this.entity.rotate(new Vector3f(0.001f*interval, 0.001f*interval, 0.0f));
  }

  @Override
  public void render(final IWindow window) {
    this.camera = Camera.builder()
        .fov((float) Math.toRadians(60))
        .width(window.getWidth())
        .height(window.getHeight())
        .zNear(0.01f)
        .zFar(1000.0f)
        .build();

    glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    this.renderer.renderEntity(this.entity, this.camera);
  }

  @Override
  public void cleanup() {
    this.shader.dispose();
  }
}
