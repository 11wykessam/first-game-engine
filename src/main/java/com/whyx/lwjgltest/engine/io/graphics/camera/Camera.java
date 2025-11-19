package com.whyx.lwjgltest.engine.io.graphics.camera;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * @author Samuel Wykes. The camera that is currently being used to view the game.
 */
public class Camera implements ICamera {

  @Getter
  @NonNull
  private Integer width, height;

  @Getter
  @NonNull
  private Float fov, zNear, zFar;

  @Getter
  private Matrix4f projectionMatrix;

  @Getter
  private Matrix4f viewMatrix;

  @Getter
  @NonNull
  private Vector3f position;

  @Getter
  @NonNull
  private Vector3f rotation;

  @Builder
  private Camera(
      final Float fov,
      final Integer width,
      final Integer height,
      final Float zNear,
      final Float zFar,
      final Vector3f position,
      final Vector3f rotation
  ) {
    this.fov = fov;
    this.width = width;
    this.height = height;
    this.zNear = zNear;
    this.zFar = zFar;
    this.position = position;
    this.rotation = rotation;
    this.updateProjectionMatrix();
    this.updateViewMatrix();
  }

  private void updateProjectionMatrix() {
    final float aspectRatio = (float) this.width / (float) this.height;
    this.projectionMatrix = new Matrix4f()
        .identity()
        .perspective(this.fov, aspectRatio, this.zNear, this.zFar);
  }

  private void updateViewMatrix() {
    this.viewMatrix = new Matrix4f()
        .identity()
        .rotateX(this.rotation.x)
        .rotateY(this.rotation.y)
        .rotateZ(this.rotation.z)
        .translate(this.position);
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
    return this.position.x;
  }

  @Override
  public Float getRotationY() {
    return this.position.y;
  }

  @Override
  public Float getRotationZ() {
    return this.position.z;
  }

  public void setFov(final Float fov) {
    this.fov = fov;
    this.updateProjectionMatrix();
  }

  public void setWidth(final Integer width) {
    this.width = width;
    this.updateProjectionMatrix();
  }

  public void setHeight(final Integer height) {
    this.height = height;
    this.updateProjectionMatrix();
  }

  public void setZNear(final Float zNear) {
    this.zNear = zNear;
    this.updateProjectionMatrix();
  }

  public void setZFar(final Float zFar) {
    this.zFar = zFar;
    this.updateProjectionMatrix();
  }

  @Override
  public void translateRelativeToView(final Vector3f translation) {
    translation.rotateX(-this.rotation.x);
    translation.rotateY(-this.rotation.y);
    translation.rotateZ(-this.rotation.z);
    this.position.add(translation);
    this.updateViewMatrix();
  }

  @Override
  public void translate(final Vector3f translation) {
    this.position.add(translation);
    this.updateViewMatrix();
  }

  @Override
  public void rotate(final Vector3f rotation) {
    this.rotation.add(rotation);
    this.updateViewMatrix();
  }

  @Override
  public void setPosition(final Vector3f position) {
    this.position = position;
    this.updateViewMatrix();
  }

  @Override
  public void setRotation(final Vector3f rotation) {
    this.rotation = rotation;
    this.updateViewMatrix();
  }

}
