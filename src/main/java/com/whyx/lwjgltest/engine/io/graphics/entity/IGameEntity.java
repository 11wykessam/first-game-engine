package com.whyx.lwjgltest.engine.io.graphics.entity;

import com.whyx.lwjgltest.engine.io.graphics.mesh.IMesh;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * @author Samuel Wykes.
 * <p>
 * Represents an entity to render.
 */
public interface IGameEntity {

  /**
   * Get the mesh for the entity.
   *
   * @return {@link IMesh}.
   */
  IMesh getMesh();

  /**
   * Get the position for the entity.
   *
   * @return {@link Vector3f}.
   */
  Vector3f getPosition();

  /**
   * Get the x coordinate of the entity.
   *
   * @return {@link Float}.
   */
  Float getX();

  /**
   * Get the y coordinate of the entity.
   *
   * @return {@link Float}.
   */
  Float getY();

  /**
   * Get the z coordinate of the entity.
   *
   * @return {@link Float}.
   */
  Float getZ();

  /**
   * Get the scale of the entity.
   *
   * @return {@code float}.
   */
  Float getScale();

  /**
   * Get the rotation for the entity.
   *
   * @return {@link Vector3f}.
   */
  Vector3f getRotation();

  /**
   * Get the x component of the rotation.
   *
   * @return {@link Float}.
   */
  Float getRotationX();

  /**
   * Get the y component of the rotation.
   *
   * @return {@link Float}.
   */
  Float getRotationY();

  /**
   * Get the z component of the rotation.
   *
   * @return {@link Float}.
   */
  Float getRotationZ();

  /**
   * The world matrix, which should be used to render the entity.
   *
   * @return {@link Vector4f}.
   */
  Matrix4f getWorldMatrix();

  /**
   * Translate entity from its current position.
   *
   * @param translation {@link Vector3f} to translate by.
   */
  void translate(final Vector3f translation);

  /**
   * Rotate the entity from its current rotation.
   *
   * @param rotation {@link Vector3f} to rotate by.
   */
  void rotate(final Vector3f rotation);

  /**
   * Scale entity from its current scale.
   *
   * @param scale factor to scale by.
   */
  void scale(final Float scale);

  /**
   * Set the position of the entity.
   *
   * @param position {@link Vector3f} position to change to.
   */
  void setPosition(final Vector3f position);

  /**
   * Set the rotation of the entity.
   *
   * @param rotation {@link Vector3f} rotation to apply.
   */
  void setRotation(final Vector3f rotation);

  /**
   * Set the scale of the entity.
   *
   * @param scale factor to scale by.
   */
  void setScale(final Float scale);

  /**
   * Initialise the entity.
   */
  void init();

  /**
   * Clean up resources used by the entity.
   */
  void cleanup();
}
