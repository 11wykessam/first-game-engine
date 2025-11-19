package com.whyx.lwjgltest.engine.io.input;

import org.joml.Vector2d;
import org.joml.Vector2f;

/**
 * @author Samuel Wykes. Represents something which can provide mouse movement callbacks.
 */
public interface IMouseMoveCallbacks {

  /**
   * Get the current position of the mouse.
   *
   * @return {@link Vector2f}.
   */
  Vector2d getMousePosition();

  /**
   * Get x coordinate of mouse during the current update.
   *
   * @return x coordinate of current mouse position.
   */
  Double getMouseX();

  /**
   * Get y coordinate of mouse during the current update.
   *
   * @return y coordinate of current mouse position.
   */
  Double getMouseY();

  /**
   * Get the difference in mouse coordinates between the last and current call.
   *
   * @return {@link Vector2d}.
   */
  Vector2d getMouseDeltaPosition();

}
