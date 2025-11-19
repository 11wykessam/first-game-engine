package com.whyx.lwjgltest.engine.io.graphics.mesh;

import com.whyx.lwjgltest.engine.io.graphics.texture.ITexture;
import java.util.Optional;

/**
 * @author Samuel Wykes. Interface representing meshes.
 */
public interface IMesh {

  void init();

  void cleanup();

  int getVertexBufferObject();

  int getIndexBufferObject();

  int getColourBufferObject();

  int getIndexCount();

  long getVertexCount();

  Optional<ITexture> getTexture();

}
