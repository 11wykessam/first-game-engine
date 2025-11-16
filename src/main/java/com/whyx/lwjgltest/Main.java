package com.whyx.lwjgltest;

import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.DEFAULT_TARGET_UPS;

import com.whyx.lwjgltest.engine.GameEngine;
import com.whyx.lwjgltest.engine.io.graphics.window.WindowOptions;
import com.whyx.lwjgltest.game.colourTest.ColourGame;
import com.whyx.lwjgltest.game.cubeTest.CubeGame;

/**
 * @author Samuel Wykes.
 */
public class Main {

  public static void main(final String[] args) {
    final GameEngine gameEngine = GameEngine.builder()
        .game(new CubeGame())
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
