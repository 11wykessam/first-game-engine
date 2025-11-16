package com.whyx.lwjgltest.engine.io.graphics.texture;

/**
 * @author Samuel Wykes.
 * Represents a texture to render to an entity.
 */
public interface ITexture {

  /**
   * Initialise the texture.
   */
  void init();

  /**
   * Bind the texture to be used during rendering.
   */
  void bind();

  /**
   * Clean up resources used by texture.
   */
  void unbind();

  /**
   * Get the id of this texture.
   * @return {@code int}.
   */
  int getTextureId();

}
