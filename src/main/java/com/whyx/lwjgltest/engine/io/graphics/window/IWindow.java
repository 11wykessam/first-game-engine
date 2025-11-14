package com.whyx.lwjgltest.engine.io.graphics.window;

import com.whyx.lwjgltest.engine.io.input.IKeyboardCallbacks;
import com.whyx.lwjgltest.engine.io.input.IMouseButtonCallbacks;
import com.whyx.lwjgltest.engine.io.input.IMouseMoveCallbacks;

/**
 * @author Samuel Wykes.
 * Interface for a window, which can be rendered to.
 */
public interface IWindow {

  /**
   * Gets the callbacks for keyboard input.
   * @return {@link IKeyboardCallbacks}.
   */
  IKeyboardCallbacks getKeyboardCallbacks();

  /**
   * Gets the callbacks for mouse button input.
   * @return {@link IMouseButtonCallbacks}.
   */
  IMouseButtonCallbacks getMouseButtonCallbacks();

  /**
   * Gets the callbacks for mouse movement input.
   * @return {@link IMouseMoveCallbacks}.}
   */
  IMouseMoveCallbacks getMouseMoveCallbacks();

  /**
   * Initialises the window.
   */
  void init();

  /**
   * Polls for window events.
   */
  void pollEvents();

  /**
   * Checks if the window should close.
   * This is normally done by interaction outside the main game loop.
   * @return {@code true} if the window should close.
   */
  boolean windowShouldClose();

  /**
   * Close the window.
   */
  void close();

  /**
   * Cleans up the window before the app closes.
   */
  void cleanup();

  /**
   * Update the window.
   */
  void update();

  /**
   * Resize the window.
   * @param width Width to resize to.
   * @param height Height to resize to.
   */
  void resize(int width, int height);

  /**
   * Enable/disable fullscreen.
   * @param fullscreen {@code true} to enable fullscreen.}
   */
  void setFullscreen(boolean fullscreen);

  /**
   * Check if the window is fullscreen.
   * @return {@code true} if the window is fullscreen.
   */
  boolean isFullscreen();

  /**
   * Get the width of the window.
   * @return The width of the window.
   */
  int getWidth();

  /**
   * Get the height of the window.
   * @return The height of the window.
   */
  int getHeight();

}
