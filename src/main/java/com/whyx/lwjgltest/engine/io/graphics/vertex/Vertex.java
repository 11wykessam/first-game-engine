package com.whyx.lwjgltest.engine.io.graphics.vertex;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.joml.Vector3f;

/**
 * @author Samuel Wykes.
 * Represents a vertex in 3D space.
 */
@AllArgsConstructor
@Builder
public class Vertex {

  /**
   * Coordinates of the vertex.
   */
  @NonNull
  @Getter
  private final Vector3f position;

  private final Vector3f colour;

  private final Vector3f texture;

  public Optional<Vector3f> getColour() {
    return Optional.ofNullable(this.colour);
  }

  public Optional<Vector3f> getTexture() {
    return Optional.ofNullable(this.texture);
  }

}
