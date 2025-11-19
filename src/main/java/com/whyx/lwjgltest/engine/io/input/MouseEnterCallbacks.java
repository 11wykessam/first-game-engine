package com.whyx.lwjgltest.engine.io.input;

import lombok.Getter;
import org.lwjgl.glfw.GLFWCursorEnterCallback;

/**
 * @author Samuel Wykes. Responsible for handling GLFW invocations involving mouse leaving/entering
 * a window.
 */
@Getter
public class MouseEnterCallbacks extends GLFWCursorEnterCallback implements IMouseEnterCallbacks {

  private boolean insideWindow;

  @Override
  public void invoke(final long window, final boolean insideWindow) {
    this.insideWindow = insideWindow;
  }

}
