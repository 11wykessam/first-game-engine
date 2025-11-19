package com.whyx.lwjgltest.engine.io.input;

import lombok.Getter;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFWCursorPosCallback;

/**
 * @author Samuel Wykes. Responsible for handling GLFW mouse movement invocations.
 */
@Getter
public class MouseMoveCallbacks extends GLFWCursorPosCallback implements IMouseMoveCallbacks {

  private Double mouseX, mouseY;
  private Double prevMouseX, prevMouseY;

  @Override
  public void invoke(
      final long window,
      final double x,
      final double y
  ) {
    this.mouseX = x;
    this.mouseY = y;
  }

  @Override
  public Vector2d getMousePosition() {
    return new Vector2d(this.mouseX, this.mouseY);
  }

  @Override
  public Vector2d getMouseDeltaPosition() {
    final double currentMouseX = this.mouseX == null ? 0.0 : this.mouseX ;
    final double currentMouseY = this.mouseY == null ? 0.0 : this.mouseY ;
    final double deltaX = currentMouseX - (this.prevMouseX == null ? currentMouseX : this.prevMouseX);
    final double deltaY = currentMouseY - (this.prevMouseY == null ? currentMouseY : this.prevMouseY);

    this.prevMouseX = this.mouseX;
    this.prevMouseY = this.mouseY;
    return new Vector2d(deltaX, deltaY);
  }

}
