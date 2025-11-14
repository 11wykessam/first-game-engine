package com.whyx.lwjgltest.engine.io.graphics.mesh;

/**
 * @author Samuel Wykes.
 * Interface representing meshes.
 */
public interface IMesh {

  void init();

  void cleanup();

  int getVertexBufferObject();

  int getIndexBufferObject();

  int getColourBufferObject();

  int getIndexCount();

  long getVertexCount();

}
