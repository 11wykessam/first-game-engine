package com.whyx.lwjgltest.engine.io.input;

/**
 * @author Samuel Wykes.
 * Represents something which can provide keyboard callbacks.
 */
public interface IKeyboardCallbacks {

  /**
   * Returns whether a given key is down during the update it is called in.
   * @param key Key code.
   * @return {@code true} if key is down.
   */
  boolean isKeyDown(final int key);

}
