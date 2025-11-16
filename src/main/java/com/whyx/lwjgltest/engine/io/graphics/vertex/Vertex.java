package com.whyx.lwjgltest.engine.io.graphics.vertex;

import static com.whyx.lwjgltest.engine.constants.ColourConstants.CLEAR_COLOUR;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * @author Samuel Wykes.
 * Represents a vertex in 3D space.
 */
@AllArgsConstructor
@Builder
public class Vertex implements IVertex {

  /**
   * Coordinates of the vertex.
   */
  @NonNull
  @Getter
  private final Vector3f position;

  @Builder.Default
  @Getter
  private final Vector4f colour = CLEAR_COLOUR;

  @Builder.Default
  @Getter
  private final Vector2f texture = new Vector2f(0.0f, 0.0f);

}
