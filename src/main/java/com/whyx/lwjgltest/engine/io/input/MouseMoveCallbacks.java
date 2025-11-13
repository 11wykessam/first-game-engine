package com.whyx.lwjgltest.engine.io.input;

import lombok.Getter;
import org.lwjgl.glfw.GLFWCursorPosCallback;

/**
 * @author Samuel Wykes.
 * Responsible for handling GLFW mouse movement invocations.
 */
@Getter
public class MouseMoveCallbacks extends GLFWCursorPosCallback implements IMouseMoveCallbacks {

  private double mouseX, mouseY;

  @Override
  public void invoke(
      final long window,
      final double x,
      final double y
  ) {
    this.mouseX = x;
    this.mouseY = y;
  }

}
