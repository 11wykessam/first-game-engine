package com.whyx.lwjgltest.engine.io.graphics.window;

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
import static org.lwjgl.glfw.GLFW.glfwGetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;

import com.whyx.lwjgltest.engine.io.input.KeyboardCallbacks;
import com.whyx.lwjgltest.engine.io.input.MouseButtonCallbacks;
import com.whyx.lwjgltest.engine.io.input.MouseMoveCallbacks;
import java.nio.IntBuffer;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL30;
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
   * Set whether the viewport should be resized.
   */
  @Setter
  private boolean shouldResize;

  @Getter
  private boolean fullscreen;

  private final int[] windowPosX = new int[1], windowPosY = new int[1];

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
            this.width,
            this.height,
            this.title,
            0,
            0
        )
    );
    if (this.windowId == 0) {
      throw new RuntimeException("Failed to create the GLFW window.");
    }

    // Get the thread stack and push a new frame
    try ( final MemoryStack stack = stackPush() ) {
      final IntBuffer pWidth = stack.mallocInt(1); // int*
      final IntBuffer pHeight = stack.mallocInt(1); // int*

      // Get the window size passed to glfwCreateWindow
      glfwGetWindowSize(this.windowId, pWidth, pHeight);

      // Get the resolution of the primary monitor
      final GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      if (vidmode != null) {
        // Center the window
        glfwSetWindowPos(
            this.windowId,
            (vidmode.width() - pWidth.get(0)) / 2,
            (vidmode.height() - pHeight.get(0)) / 2
        );
      }
    } // the stack frame is popped automatically

    // Make the OpenGL context current
    glfwMakeContextCurrent(this.windowId);
    createCapabilities();
    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(this.windowId);
  }

  /**
   * Set up the input callbacks.
   */
  private void initCallbacks() {
    glfwSetKeyCallback(this.windowId, this.keyboardCallbacks);
    glfwSetMouseButtonCallback(this.windowId, this.mouseButtonCallbacks);
    glfwSetCursorPosCallback(this.windowId, this.mouseMoveCallbacks);
    glfwSetWindowSizeCallback(this.windowId, (window, width, height) -> {
      this.resize(width, height);
    });
  }

  /**
   * Poll for GFLW events.
   */
  @Override
  public void pollEvents() {
    glfwPollEvents();
  }

  /**
   * Check whether the window should close.
   * @return {@code true} if the window should close.
   */
  @Override
  public boolean windowShouldClose() {
    return glfwWindowShouldClose(this.windowId);
  }

  /**
   * Close the window.
   */
  @Override
  public void close() {
    GLFW.glfwSetWindowShouldClose(this.windowId, true);
  }

  /**
   * Swap GLFW buffers.
   */
  @Override
  public void update() {
    glfwSwapBuffers(this.windowId);
    if (this.shouldResize) {
      glViewport(0, 0, this.width, this.height);
      this.setShouldResize(false);
    }
  }

  /**
   * Free up resources used by the window.
   */
  public void cleanup() {
    // Destroy the window.
    glfwFreeCallbacks(this.windowId);
    glfwDestroyWindow(this.windowId);

    // Terminate GLFW.
    glfwTerminate();
    Objects.requireNonNull(glfwSetErrorCallback(null)).free();
  }
  
  public void resize(final int width, final int height) {
    this.setWidth(width);
    this.setHeight(height);
    this.setShouldResize(true);
  }

  public void setFullscreen(final boolean fullscreen) {
    this.fullscreen = fullscreen;
    this.shouldResize = true;

    if (fullscreen) {
      final long monitor = glfwGetPrimaryMonitor();
      final GLFWVidMode vidMode = glfwGetVideoMode(monitor);

      if (vidMode != null) {
        glfwGetWindowPos(this.windowId, this.windowPosX, this.windowPosY);
        glfwSetWindowMonitor(this.windowId, monitor, 0, 0, vidMode.width(), vidMode.height(), 0);
      }
      else {
        glfwSetWindowMonitor(this.windowId, 0, this.windowPosX[0], this.windowPosY[0], this.width,
            this.height, 0);
      }
    }
  }

}
