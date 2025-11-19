package com.whyx.lwjgltest.engine.io.input;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 * @author Samuel Wykes. Responsible for handling GLFW mouse button invocations.
 */
public class MouseButtonCallbacks extends GLFWMouseButtonCallback implements IMouseButtonCallbacks {

  private final boolean[] mouseButtons = new boolean[GLFW_MOUSE_BUTTON_LAST];

  @Override
  public void invoke(
      final long window,
      final int button,
      final int action,
      final int mods
  ) {
    this.mouseButtons[button] = action != GLFW_RELEASE;
  }

  public boolean isMouseButtonDown(final int button) {
    return this.mouseButtons[button];
  }

}
