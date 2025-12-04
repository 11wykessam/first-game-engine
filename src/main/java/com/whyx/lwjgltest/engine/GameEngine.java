package com.whyx.lwjgltest.engine;

import com.whyx.lwjgltest.engine.io.IGameLogic;
import com.whyx.lwjgltest.engine.io.graphics.window.IWindow;
import com.whyx.lwjgltest.engine.io.graphics.window.Window;
import com.whyx.lwjgltest.engine.io.graphics.window.WindowOptions;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author Samuel Wykes. Responsible for running the game logic.
 */
@Getter
public class GameEngine implements Runnable {

  /**
   * The game to be run by the engine.
   */
  @NonNull
  private final IGameLogic game;

  /**
   * The window to run the game in.
   */
  @NonNull
  private final IWindow window;

  /**
   * The target FPS for the game. If 0, go for screen refresh rate.
   */
  @NonNull
  private final int targetFps;

  /**
   * The target UPS for the game. 30 by default.
   */
  private final int targetUps;

  /**
   * The current FPS of the game.
   */
  @Setter
  private int fps;

  /**
   * The current UPS of the game.
   */
  @Setter
  private int ups;

  /**
   * Whether the game is running or not.
   */
  @Setter
  private boolean running;

  @Builder
  private GameEngine(
      final IGameLogic game,
      final WindowOptions windowOptions,
      final int targetUps
  ) {
    this.game = game;
    this.window = new Window(
        windowOptions.getTitle(),
        windowOptions.getWidth(),
        windowOptions.getHeight(),
        windowOptions.getTargetFps()
    );
    this.targetFps = windowOptions.getTargetFps();
    this.targetUps = targetUps;
    this.running = true;
  }

  /**
   * Start the game.
   */
  @Override
  public void run() {
    this.window.init();
    try {
      this.game.init(this.window);
    } catch (final Exception e) {
      System.err.println("Failed to initialize game");
      e.printStackTrace();
      return;
    }
    this.loop();
    this.cleanup();
  }

  /**
   * The main game loop.
   */
  private void loop() {
    final long initialTime = System.currentTimeMillis();
    final float updateInterval = 1000.0f / this.getTargetUps();
    final float renderInterval = 1000.0f / this.getTargetFps();
    long lastUpdate = initialTime;
    long lastRender = initialTime;
    long lastSecond = initialTime;
    int updateCounter = 0;
    int renderCounter = 0;

    while (this.isRunning() && !this.getWindow().windowShouldClose()) {
      this.getWindow().pollEvents();

      final long now = System.currentTimeMillis();

      final long deltaUpdateTime = now - lastUpdate;
      final boolean shouldUpdate = deltaUpdateTime >= updateInterval;
      final boolean shouldRender = this.getTargetFps() == 0 || now - lastRender >= renderInterval;
      final boolean secondElapsed = now - lastSecond >= 1000.0f;

      if (shouldRender) {
        this.getGame().input(this.getWindow());
      }

      if (shouldUpdate) {
        this.getGame().update(this.getWindow(), deltaUpdateTime);
        updateCounter++;
        lastUpdate = now;
      }

      if (shouldRender) {
        this.getGame().render(this.getWindow());
        this.getWindow().update();
        renderCounter++;
        lastRender = now;
      }

      if (secondElapsed) {
        this.setFps(renderCounter);
        this.setUps(updateCounter);
        updateCounter = 0;
        renderCounter = 0;
        lastSecond = now;
//        System.out.println("FPS: " + this.getFps() + " UPS: " + this.getUps());
      }

    }
  }

  /**
   * Clean up the game.
   */
  private void cleanup() {
    this.getGame().cleanup();
    // this needs to go last.
    this.getWindow().cleanup();
  }

}
