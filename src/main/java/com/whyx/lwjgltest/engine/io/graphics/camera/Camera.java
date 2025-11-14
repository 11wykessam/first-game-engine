package com.whyx.lwjgltest.engine.io.graphics.camera;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;

/**
 * @author Samuel Wykes.
 * The camera that is currently being used to view the game.
 */
public class Camera implements ICamera{

  @Getter
  @NonNull
  private Float fov, width, height, zNear, zFar;

  @Getter
  private Matrix4f projectionMatrix;

  @Builder
  private Camera(
      final float fov,
      final float width,
      final float height,
      final float zNear,
      final float zFar
  ) {
    this.fov = fov;
    this.width = width;
    this.height = height;
    this.zNear = zNear;
    this.zFar = zFar;
    this.updateProjectionMatrix();
  }

  private void updateProjectionMatrix() {
    final float aspectRatio = this.width / this.height;
    this.projectionMatrix = new Matrix4f()
        .identity()
        .perspective(this.fov, aspectRatio, this.zNear, this.zFar);
  }

  public void setFov(final Float fov) {
    this.fov = fov;
    this.updateProjectionMatrix();
  }

  public void setWidth(final Float width) {
    this.width = width;
    this.updateProjectionMatrix();
  }

  public void setHeight(final Float height) {
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

}
