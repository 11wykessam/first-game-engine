package com.whyx.lwjgltest.engine.io.graphics.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * @author Samuel Wykes. Represents the camera currently viewing the game.
 */
public interface ICamera {

  /**
   * Get the current field-of-view of the camera.
   *
   * @return Fov in radians.
   */
  Float getFov();

  /**
   * Get the current width of the projection matrix. Should normally be the width of the screen.
   *
   * @return Width of the display space.
   */
  Integer getWidth();

  /**
   * Get the current height of the projection matrix. Should normally be the width of the screen.
   *
   * @return Height of the display space.
   */
  Integer getHeight();

  /**
   * Get nearest z coordinates to render.
   *
   * @return Nearest z coordinate to render.
   */
  Float getZNear();

  /**
   * Get furthest z coordinate to render.
   *
   * @return Furthest z coordinate to render.
   */
  Float getZFar();

  /**
   * Get the position of the camera.
   *
   * @return {@link Vector3f}.
   */
  Vector3f getPosition();

  /**
   * Get the x coordinate of the camera.
   *
   * @return {@link Float}
   */
  Float getX();

  /**
   * Get the y coordinate of the camera.
   *
   * @return {@link Float}.
   */
  Float getY();

  /**
   * Get the z coordinate of the camera.
   *
   * @return {@link Float}.
   */
  Float getZ();

  /**
   * Get the rotation of the camera in radians.
   *
   * @return {@link Vector3f}.
   */
  Vector3f getRotation();

  /**
   * Get the rotation x component of the camera in radians.
   *
   * @return {@link Float}.
   */
  Float getRotationX();

  /**
   * & Get the rotation y component of the camera in radians.
   *
   * @return {@link Float}.
   */
  Float getRotationY();

  /**
   * Get the rotation z component of the camera in radians.
   *
   * @return {@link Float}.
   */
  Float getRotationZ();

  /**
   * Get the projection matrix.
   *
   * @return {@link Matrix4f}.
   */
  Matrix4f getProjectionMatrix();

  /**
   * Get the view matrix.
   *
   * @return {@link Matrix4f}.
   */
  Matrix4f getViewMatrix();

  /**
   * Set the field of view for the camera.
   *
   * @param fov {@link Float}.
   */
  void setFov(final Float fov);

  /**
   * Set the width of the display space.
   *
   * @param width {@link Float}.
   */
  void setWidth(final Integer width);

  /**
   * Set the height of the display space.
   *
   * @param height {@link Float}.
   */
  void setHeight(final Integer height);

  /**
   * Set the zNear for the camera.
   *
   * @param zNear {@link Float}.
   */
  void setZNear(final Float zNear);

  /**
   * Set the zFar for the camera.
   *
   * @param zFar {@link Float}.
   */
  void setZFar(final Float zFar);

  /**
   * Translate the camera by a given vector.
   * This will translate the camera based on the current rotation.
   *
   * @param translation {@link Vector3f}.
   */
  void translateRelativeToView(final Vector3f translation);

  /**
   * Translate the camera by a given vector.
   *
   * @param translation {@link Vector3f}.
   */
  void translate(final Vector3f translation);

  /**
   * Rotate the camera by a given vector of radians.
   *
   * @param rotation {@link Vector3f}.
   */
  void rotate(final Vector3f rotation);

  /**
   * Sets the position of the camera.
   *
   * @param position {@link Vector3f}
   */
  void setPosition(final Vector3f position);

  /**
   * Set the rotation of the camera in radians.
   *
   * @param rotation {@link Vector3f}.
   */
  void setRotation(final Vector3f rotation);

}
