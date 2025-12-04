package com.whyx.lwjgltest.engine.utils.meshUtils;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.joml.Vector3i;

/**
 * @author Samuel Wykes.
 * Represents a face in an .obj file.
 */
@Getter
public class ObjFace {

  final List<Vector3i> vertexIndices = new ArrayList<>();

  public void addIndex(final Vector3i vertex) {
    this.vertexIndices.add(vertex);
  }

}
