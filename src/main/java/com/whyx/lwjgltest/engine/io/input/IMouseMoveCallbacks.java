package com.whyx.lwjgltest.engine.io.input;

/**
 * @author Samuel Wykes.
 * Represents something which can provide mouse movement callbacks.
 */
public interface IMouseMoveCallbacks {

  /**
   * Get x coordinate of mouse during the current update.
   * @return x coordinate of current mouse position.
   */
  double getMouseX();

  /**
   * Get y coordinate of mouse during the current update.
   * @return y coordinate of current mouse position.
   */
  double getMouseY();

}
