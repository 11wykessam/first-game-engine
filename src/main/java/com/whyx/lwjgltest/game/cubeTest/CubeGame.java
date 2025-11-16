package com.whyx.lwjgltest.game.cubeTest;

import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.PROJECTION_MATRIX_UNIFORM;
import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.TEXTURE_SAMPLER_UNIFORM;
import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.WORLD_MATRIX_UNIFORM;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import com.whyx.lwjgltest.engine.io.IGameLogic;
import com.whyx.lwjgltest.engine.io.graphics.camera.Camera;
import com.whyx.lwjgltest.engine.io.graphics.camera.ICamera;
import com.whyx.lwjgltest.engine.io.graphics.entity.GameEntity;
import com.whyx.lwjgltest.engine.io.graphics.entity.IGameEntity;
import com.whyx.lwjgltest.engine.io.graphics.renderer.IRenderer;
import com.whyx.lwjgltest.engine.io.graphics.renderer.Renderer;
import com.whyx.lwjgltest.engine.io.graphics.shader.Shader;
import com.whyx.lwjgltest.engine.io.graphics.texture.Texture;
import com.whyx.lwjgltest.engine.io.graphics.window.IWindow;
import java.util.List;
import org.joml.Vector3f;

/**
 * @author Samuel Wykes.
 */
public class CubeGame implements IGameLogic {

  private IGameEntity entity;

  private Shader shader;

  private IRenderer renderer;

  private ICamera camera;

  @Override
  public void init() throws Exception {
    this.shader = new Shader(
        "/shaders/cubeVertex.glsl",
        "/shaders/cubeFragment.glsl",
        List.of(PROJECTION_MATRIX_UNIFORM, WORLD_MATRIX_UNIFORM, TEXTURE_SAMPLER_UNIFORM)
    );
    this.renderer = Renderer.builder().shader(this.shader).build();

    final Texture texture = new Texture("src/main/resources/textures/cube.png");

    this.entity = GameEntity.builder()
        .mesh(new CubeMesh(texture))
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

  }
}
