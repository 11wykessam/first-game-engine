package com.whyx.lwjgltest.engine.io.input;

/**
 * @author Samuel Wykes. Represents something which can provide mouse enter callbacks.
 */
public interface IMouseEnterCallbacks {

  /**
   * Returns whether the mouse is currently inside the window.
   *
   * @return {@code true} if the mouse is currently inside the window.
   */
  boolean isInsideWindow();

}
