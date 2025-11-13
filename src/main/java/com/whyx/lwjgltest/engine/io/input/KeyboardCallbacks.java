package com.whyx.lwjgltest.engine.io.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWKeyCallback;

/**
 * @author Samuel Wykes.
 * Responsible for handling GLFW keyboard invocations.
 */
public class KeyboardCallbacks extends GLFWKeyCallback implements IKeyboardCallbacks {

  private final boolean[] keysDown = new boolean[GLFW_KEY_LAST];

  @Override
  public void invoke(
      final long window,
      final int key,
      final int scanCode,
      final int action,
      final int mods
  ) {
    switch (action) {
      case GLFW_PRESS:
        this.keysDown[key] = true;
        break;
      case GLFW_RELEASE:
        this.keysDown[key] = false;
    }
  }

  public boolean isKeyDown(final int key) {
    return this.keysDown[key];
  }

}
