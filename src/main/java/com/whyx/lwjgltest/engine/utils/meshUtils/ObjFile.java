package com.whyx.lwjgltest.engine.utils.meshUtils;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * @author Samuel Wykes.
 * Represents the contents of an .obj file.
 */
@Getter
public class ObjFile {

  private final List<Vector3f> positions = new ArrayList<>();
  private final List<Vector3f> normals = new ArrayList<>();
  private final List<Vector2f> textureCoords = new ArrayList<>();
  private final List<ObjFace> faces = new ArrayList<>();

  public void addVertex(final Vector3f vertex) {
    this.positions.add(vertex);
  }

  public void addNormal(final Vector3f normal) {
    this.normals.add(normal);
  }

  public void addTextureCoord(final Vector2f textureCoord) {
    this.textureCoords.add(textureCoord);
  }

  public void addFace(final ObjFace index) {
    this.faces.add(index);
  }

}
