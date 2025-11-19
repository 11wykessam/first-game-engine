package com.whyx.lwjgltest.engine.io.input;

/**
 * @author Samuel Wykes. Represents something which can provide mouse button callbacks.
 */
public interface IMouseButtonCallbacks {

  /**
   * Returns whether a mouse button is down during the current update.
   *
   * @param button Button code.
   * @return {@code true} if button is down.
   */
  boolean isMouseButtonDown(final int button);

}
