package com.whyx.lwjgltest.engine.io.graphics.entity;

import com.whyx.lwjgltest.engine.io.graphics.mesh.IMesh;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * @author Samuel Wykes. Represents an entity to render in the game.
 */
public class GameEntity implements IGameEntity {

  @Getter
  @NonNull
  private final IMesh mesh;

  @Getter
  @NonNull
  private Vector3f position, rotation;

  @Getter
  @NonNull
  private Float scale;

  @Getter
  private Matrix4f worldMatrix;

  @Builder
  private GameEntity(
      final IMesh mesh,
      final Vector3f position,
      final Vector3f rotation,
      final Float scale
  ) {
    this.mesh = mesh;
    this.position = position;
    this.rotation = rotation;
    this.scale = scale;
    this.updateWorldMatrix();
  }

  private void updateWorldMatrix() {
    this.worldMatrix = new Matrix4f()
        .identity()
        .translate(this.position)
        .rotateX(this.rotation.x)
        .rotateY(this.rotation.y)
        .rotateZ(this.rotation.z)
        .scale(this.scale);
  }

  @Override
  public Float getX() {
    return this.position.x;
  }

  @Override
  public Float getY() {
    return this.position.y;
  }

  @Override
  public Float getZ() {
    return this.position.z;
  }

  @Override
  public Float getRotationX() {
    return this.rotation.x;
  }

  @Override
  public Float getRotationY() {
    return this.rotation.y;
  }

  @Override
  public Float getRotationZ() {
    return this.rotation.z;
  }

  @Override
  public void translate(final Vector3f translation) {
    this.position.add(translation);
    this.updateWorldMatrix();
  }

  @Override
  public void rotate(final Vector3f rotation) {
    this.rotation.add(rotation);
    this.updateWorldMatrix();
  }

  @Override
  public void scale(final Float scale) {
    this.scale *= scale;
    this.updateWorldMatrix();
  }

  public void setPosition(final Vector3f position) {
    this.position = position;
    this.updateWorldMatrix();
  }

  public void setRotation(final Vector3f rotation) {
    this.rotation = rotation;
    this.updateWorldMatrix();
  }

  public void setScale(final Float scale) {
    this.scale = scale;
    this.updateWorldMatrix();
  }
}
