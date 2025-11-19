package com.whyx.lwjgltest.engine.io.graphics.window;

import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.DEFAULT_TARGET_FPS;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author Samuel Wykes. Responsible for holding options to pass to a {@link Window}.
 */
@Builder
@Getter
public class WindowOptions {

  /**
   * The title of the window.
   */
  @NonNull
  private final String title;

  /**
   * The dimensions of the window.
   */
  @NonNull
  private final int width, height;

  /**
   * The target FPS for the window. If 0, go for screen refresh rate.
   */
  @Builder.Default
  private final int targetFps = DEFAULT_TARGET_FPS;

}
