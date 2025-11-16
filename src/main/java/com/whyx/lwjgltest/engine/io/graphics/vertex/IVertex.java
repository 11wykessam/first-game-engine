package com.whyx.lwjgltest.engine.io.graphics.vertex;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * @author Samuel Wykes.
 * Represents vertices in 3D space.
 */
public interface IVertex {

  /**
   * Get the position of the vertex.
   * @return {@link Vector3f}.
   */
  Vector3f getPosition();

  /**
   * Get the colour of the vertex.
   * @return {@link Vector4f}.
   */
  Vector4f getColour();

  /**
   * Get the texture coordinates of the vertex.
   * @return {@link Vector2f}.
   */
  Vector2f getTexture();

}
