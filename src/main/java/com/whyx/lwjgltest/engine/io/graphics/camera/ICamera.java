package com.whyx.lwjgltest.engine.io.graphics.camera;

import org.joml.Matrix4f;

/**
 * @author Samuel Wykes.
 * Represents the camera currently viewing the game.
 */
public interface ICamera {

  /**
   * Get the current field-of-view of the camera.
   * @return Fov in radians.
   */
  Float getFov();

  /**
   * Get the current width of the projection matrix.
   * Should normally be the width of the screen.
   * @return Width of the display space.
   */
  Float getWidth();

  /**
   * Get the current height of the projection matrix.
   * Should normally be the width of the screen.
   * @return Height of the display space.
   */
  Float getHeight();

  /**
   * Get nearest z coordinates to render.
   * @return Nearest z coordinate to render.
   */
  Float getZNear();

  /**
   * Get furthest z coordinate to render.
   * @return Furthest z coordinate to render.
   */
  Float getZFar();

  /**
   * Get the projection matrix.
   * @return {@link Matrix4f}.
   */
  Matrix4f getProjectionMatrix();

  /**
   * Set the field of view for the camera.
   */
  void setFov(final Float fov);

  /**
   * Set the width of the display space.
   */
  void setWidth(final Float width);

  /**
   * Set the height of the display space.
   */
  void setHeight(final Float height);

  /**
   * Set the zNear for the camera.
   */
  void setZNear(final Float zNear);

  /**
   * Set the zFar for the camera.
   */
  void setZFar(final Float zFar);

}
