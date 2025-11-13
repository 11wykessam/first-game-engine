package com.whyx.lwjgltest.engine.io.graphics.mesh;

import com.whyx.lwjgltest.engine.io.graphics.renderer.Renderer;
import com.whyx.lwjgltest.engine.io.graphics.vertex.Vertex;
import com.whyx.lwjgltest.engine.io.graphics.window.IWindow;
import java.util.List;
import lombok.Builder;

/**
 * @author Samuel Wykes.
 * A mesh with no I/O.
 */
public class SimpleMesh extends Mesh {

  @Builder
  private SimpleMesh(final List<Vertex> vertices, final List<Integer> indices, final Renderer renderer) {
    super(vertices, indices, renderer);
  }

  @Override
  public void init() {

  }

  @Override
  public void input(final IWindow window) {

  }

  @Override
  public void update(final IWindow window, final float interval) {

  }
}
