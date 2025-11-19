package com.whyx.lwjgltest.engine.io.graphics.renderer;

import com.whyx.lwjgltest.engine.io.graphics.camera.ICamera;
import com.whyx.lwjgltest.engine.io.graphics.entity.IGameEntity;

/**
 * @author Samuel Wykes.
 * <p>
 * Interface for a renderer.
 */
public interface IRenderer {

  void renderEntity(final IGameEntity entity, final ICamera camera);

}
