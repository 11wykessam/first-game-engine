package com.whyx.lwjgltest;

import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.DEFAULT_TARGET_UPS;

import com.whyx.lwjgltest.engine.GameEngine;
import com.whyx.lwjgltest.engine.io.IGameLogic;
import com.whyx.lwjgltest.engine.io.graphics.IWindow;
import com.whyx.lwjgltest.engine.io.graphics.Window;
import com.whyx.lwjgltest.engine.io.graphics.WindowOptions;

/**
 * @author Samuel Wykes.
 */
public class Main {

  private static IGameLogic gameLogic = new IGameLogic() {
    @Override
    public void init() throws Exception {

    }

    @Override
    public void input(IWindow window) {

    }

    @Override
    public void update(IWindow window, float interval) {

    }

    @Override
    public void render(IWindow window) {

    }
  };

  public static void main(final String[] args) {
    final GameEngine gameEngine = GameEngine.builder()
        .game(gameLogic)
        .windowOptions(WindowOptions.builder()
            .title("3d game test")
            .width(300)
            .height(300)
            .targetFps(60)
            .build())
        .targetUps(DEFAULT_TARGET_UPS)
        .build();
    final Thread gameThread = new Thread(gameEngine, "game");
    gameThread.start();
  }
}
