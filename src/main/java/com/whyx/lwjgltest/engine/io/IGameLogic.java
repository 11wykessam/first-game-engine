package com.whyx.lwjgltest.engine.io;

import com.whyx.lwjgltest.engine.io.graphics.IWindow;

/**
 * @author Samuel Wykes.
 * Represents all things which have logic in a game.
 */
public interface IGameLogic {

  void init() throws Exception;

  void input(final IWindow window);

  void update(final IWindow window, final float interval);

  void render(final IWindow window);

}
