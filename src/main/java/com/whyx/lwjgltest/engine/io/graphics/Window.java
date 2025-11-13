package com.whyx.lwjgltest.engine.io.graphics;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryStack.stackPush;

import com.whyx.lwjgltest.engine.io.input.KeyboardCallbacks;
import com.whyx.lwjgltest.engine.io.input.MouseButtonCallbacks;
import com.whyx.lwjgltest.engine.io.input.MouseMoveCallbacks;
import java.nio.IntBuffer;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

/**
 * @author Samuel Wykes.
 * Class responsible for rendering a game window.
 */
public class Window implements IWindow {

  /**
   * The current title of the window.
   */
  @Getter
  @Setter
  private String title;

  /**
   * The current dimensions of the window.
   */
  @Getter
  @Setter
  private int width, height;

  /**
   * The target FPS for the window. If 0, go for screen refresh rate.
   */
  @Getter
  private final int targetFps;

  /**
   * Responsible for handling GLFW keyboard invocations.
   */
  @Getter
  private final KeyboardCallbacks keyboardCallbacks;

  /**
   * Responsible for handling GLFW mouse button invocations.
   */
  @Getter
  private final MouseButtonCallbacks mouseButtonCallbacks;

  /**
   * Responsible for handling GLFW mouse movement invocations.
   */
  @Getter
  private final MouseMoveCallbacks mouseMoveCallbacks;

  /**
   * Reference to the GLFW window.
   */
  @Getter
  @Setter
  private long windowId;

  /**
   * Constructor.
   * @param title Initial window title.
   * @param width Initial window width.
   * @param height Initial window height.
   * @param targetFps Target FPS for the window. If 0, go for screen refresh rate.
   */
  public Window(
      final String title,
      final int width,
      final int height,
      final int targetFps
  ) {
    this.title = title;
    this.width = width;
    this.height = height;
    this.targetFps = targetFps;
    this.keyboardCallbacks = new KeyboardCallbacks();
    this.mouseButtonCallbacks = new MouseButtonCallbacks();
    this.mouseMoveCallbacks = new MouseMoveCallbacks();
  }

  public void init() {
    this.initWindow();
    this.initCallbacks();
  }

  private void initWindow() {
    // Setup error callback for GLFW.
    GLFWErrorCallback.createPrint(System.err).set();

    // Initialise GLFW.
    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW.");
    }

    // Configure GLFW.
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    // Create the window.
    this.setWindowId(
        glfwCreateWindow(
            this.getWidth(),
            this.getHeight(),
            this.getTitle(),
            0,
            0
        )
    );
    if (this.getWindowId() == 0) {
      throw new RuntimeException("Failed to create the GLFW window.");
    }

    // Get the thread stack and push a new frame
    try ( final MemoryStack stack = stackPush() ) {
      final IntBuffer pWidth = stack.mallocInt(1); // int*
      final IntBuffer pHeight = stack.mallocInt(1); // int*

      // Get the window size passed to glfwCreateWindow
      glfwGetWindowSize(this.getWindowId(), pWidth, pHeight);

      // Get the resolution of the primary monitor
      final GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      if (vidmode != null) {
        // Center the window
        glfwSetWindowPos(
            this.getWindowId(),
            (vidmode.width() - pWidth.get(0)) / 2,
            (vidmode.height() - pHeight.get(0)) / 2
        );
      }
    } // the stack frame is popped automatically

    // Make the OpenGL context current
    glfwMakeContextCurrent(this.getWindowId());
    createCapabilities();
    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(this.getWindowId());
  }

  /**
   * Set up the input callbacks.
   */
  private void initCallbacks() {
    glfwSetKeyCallback(this.getWindowId(), this.getKeyboardCallbacks());
    glfwSetMouseButtonCallback(this.getWindowId(), this.getMouseButtonCallbacks());
    glfwSetCursorPosCallback(this.getWindowId(), this.getMouseMoveCallbacks());
  }

  /**
   * Poll for GFLW events.
   */
  @Override
  public void pollEvents() {
    glfwPollEvents();
  }

  /**
   * Check whether window should close.
   * @return {@code true} if window should close.
   */
  @Override
  public boolean windowShouldClose() {
    return glfwWindowShouldClose(this.getWindowId());
  }

  /**
   * Swap GLFW buffers.
   */
  @Override
  public void update() {
    glfwSwapBuffers(this.getWindowId());
  }

  /**
   * Free up resources used by window.
   */
  public void cleanup() {
    // Destroy the window.
    glfwFreeCallbacks(this.getWindowId());
    glfwDestroyWindow(this.getWindowId());

    // Terminate GLFW.
    glfwTerminate();
    Objects.requireNonNull(glfwSetErrorCallback(null)).free();
  }

}
