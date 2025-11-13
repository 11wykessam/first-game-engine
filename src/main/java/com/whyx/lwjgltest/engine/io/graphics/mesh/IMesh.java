package com.whyx.lwjgltest.engine.io.graphics.mesh;

import com.whyx.lwjgltest.engine.io.graphics.vertex.Vertex;
import java.util.List;

/**
 * @author Samuel Wykes.
 * Interface representing meshes.
 */
public interface IMesh {

  List<Vertex> getVertices();

  List<Integer> getIndices();

  int getVertexBufferObject();

  int getIndexBufferObject();

  int getColourBufferObject();

}
