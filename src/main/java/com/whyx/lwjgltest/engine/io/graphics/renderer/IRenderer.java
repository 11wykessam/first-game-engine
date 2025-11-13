package com.whyx.lwjgltest.engine.io.graphics.renderer;

import com.whyx.lwjgltest.engine.io.graphics.mesh.IMesh;

/**
 * @author Samuel Wykes.
 *
 * Interface for a renderer.
 */
public interface IRenderer {

  void renderMesh(final IMesh mesh);

}
