package com.whyx.lwjgltest.game.cubeTest;

import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.PROJECTION_MATRIX_UNIFORM;
import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.TEXTURE_SAMPLER_UNIFORM;
import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.VIEW_MATRIX_UNIFORM;
import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.WORLD_MATRIX_UNIFORM;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
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
import org.joml.Vector2d;
import org.joml.Vector3f;

/**
 * @author Samuel Wykes.
 */
public class CubeGame implements IGameLogic {

  private static final Float SPEED = 0.001f;
  private static final Float ROTATION_SPEED = 0.001f;

  private IGameEntity entity;

  private Shader shader;

  private IRenderer renderer;

  private ICamera camera;

  @Override
  public void init(final IWindow window) throws Exception {
    this.shader = new Shader(
        "/shaders/cubeVertex.glsl",
        "/shaders/cubeFragment.glsl",
        List.of(
            PROJECTION_MATRIX_UNIFORM,
            VIEW_MATRIX_UNIFORM,
            WORLD_MATRIX_UNIFORM,
            TEXTURE_SAMPLER_UNIFORM
        )
    );
    this.renderer = Renderer.builder().shader(this.shader).build();

    final Texture texture = new Texture("/textures/cube.png");

    this.camera = Camera.builder()
        .fov((float) Math.toRadians(60))
        .width(window.getWidth())
        .height(window.getHeight())
        .zNear(0.01f)
        .zFar(1000.0f)
        .position(new Vector3f(0.0f, 0.0f, 0.0f))
        .rotation(new Vector3f(0.0f, 0.0f, 0.0f))
        .build();

    this.entity = GameEntity.builder()
        .mesh(new CubeMesh(texture))
        .scale(1f)
        .rotation(new Vector3f(0.0f, 0.0f, 0.0f))
        .position(new Vector3f(0.0f, 0.0f, -2.0f))
        .build();
    this.shader.init();

    System.gc();
  }

  @Override
  public void input(final IWindow window) {

  }

  @Override
  public void update(final IWindow window, final float interval) {
    final float speed = SPEED * interval;
    final float rotationSpeed = ROTATION_SPEED * interval;
    if (window.getKeyboardCallbacks().isKeyDown(GLFW_KEY_ESCAPE) && !window.windowShouldClose()) {
      window.close();
    }
    if (window.getKeyboardCallbacks().isKeyDown(GLFW_KEY_F11) && !window.isFullscreen()) {
      window.setFullscreen(!window.isFullscreen());
    }
    if (window.getKeyboardCallbacks().isKeyDown(GLFW_KEY_A)) {
      this.camera.translateRelativeToView(new Vector3f(speed, 0.0f, 0.0f));
    }
    if (window.getKeyboardCallbacks().isKeyDown(GLFW_KEY_D)) {
      this.camera.translateRelativeToView(new Vector3f(-speed, 0.0f, 0.0f));
    }
    if (window.getKeyboardCallbacks().isKeyDown(GLFW_KEY_S)) {
      this.camera.translateRelativeToView(new Vector3f(0.0f, 0.0f, -speed));
    }
    if (window.getKeyboardCallbacks().isKeyDown(GLFW_KEY_W)) {
      this.camera.translateRelativeToView(new Vector3f(0.0f, 0.0f, speed));
    }
    if (window.getKeyboardCallbacks().isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
      this.camera.translate(new Vector3f(0.0f, -speed, 0.0f));
    }
    if (window.getKeyboardCallbacks().isKeyDown(GLFW_KEY_LEFT_CONTROL)) {
      this.camera.translate(new Vector3f(0.0f, speed, 0.0f));
    }

    final Vector2d deltaPosition = window.getMouseMoveCallbacks().getMouseDeltaPosition();
    if (window.getMouseEnterCallbacks().isInsideWindow()
        && window.getMouseButtonCallbacks().isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
      this.camera.rotate(new Vector3f(
          (float) Math.toRadians(deltaPosition.y * rotationSpeed),
          (float) Math.toRadians(deltaPosition.x * rotationSpeed),
          0.0f
      ));
    }

    if (this.camera.getWidth() != window.getWidth()
        || this.camera.getHeight() != window.getHeight()) {
      this.camera.setWidth(window.getWidth());
      this.camera.setHeight(window.getHeight());
    }

//    this.entity.rotate(new Vector3f(0.001f * interval, 0.001f * interval, 0.0f));
  }

  @Override
  public void render(final IWindow window) {

    glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    this.renderer.renderEntity(this.entity, this.camera);
  }

  @Override
  public void cleanup() {

  }
}
