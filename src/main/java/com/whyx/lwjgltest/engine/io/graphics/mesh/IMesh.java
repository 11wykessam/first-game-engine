package com.whyx.lwjgltest.engine.io.graphics.mesh;

import com.whyx.lwjgltest.engine.io.graphics.texture.ITexture;
import java.util.Optional;

/**
 * @author Samuel Wykes. Interface representing meshes.
 */
public interface IMesh {

  void init();

  void cleanup();

  Integer getVertexBufferObject();

  Integer getIndexBufferObject();

  int getIndexCount();

  long getVertexCount();

  Optional<ITexture> getTexture();

}
