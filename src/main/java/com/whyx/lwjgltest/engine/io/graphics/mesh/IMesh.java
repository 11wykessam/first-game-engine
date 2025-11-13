package com.whyx.lwjgltest.engine.io.graphics.mesh;

import com.whyx.lwjgltest.engine.io.graphics.vertex.Vertex;
import java.util.List;

/**
 * @author Samuel Wykes.
 * Interface representing meshes.
 */
public interface IMesh {

  int getVertexBufferObject();

  int getIndexBufferObject();

  int getColourBufferObject();

  long getVertexCount();

  long getColourCount();

  long getTextureCount();

}
