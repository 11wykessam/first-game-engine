package com.whyx.lwjgltest.engine.io.graphics.texture;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_1D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import lombok.Builder;
import lombok.Getter;
import org.lwjgl.system.MemoryStack;

/**
 * @author Samuel Wykes. A texture to render to a mesh.
 */
public class Texture implements ITexture {

  private final String texturePath;

  @Getter
  private int textureId;

  @Builder
  public Texture(final String texturePath) {
    this.texturePath = texturePath;
  }

  /**
   * Initialise the texture.
   */
  @Override
  public void init() {
    try (final MemoryStack stack = MemoryStack.stackPush()) {
      final IntBuffer widthBuffer = stack.mallocInt(1);
      final IntBuffer heightBuffer = stack.mallocInt(1);
      final IntBuffer channelBuffer = stack.mallocInt(1);

      final ByteBuffer buffer = stbi_load(
          this.texturePath,
          widthBuffer,
          heightBuffer,
          channelBuffer,
          4
      );
      if (buffer == null) {
        throw new RuntimeException(
            "Failed to load a texture file: " + this.texturePath + ", " + stbi_failure_reason());
      }

      final int width = widthBuffer.get();
      final int height = heightBuffer.get();

      this.textureId = glGenTextures();
      glBindTexture(GL_TEXTURE_1D, this.textureId);
      glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
      glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
      glGenerateMipmap(GL_TEXTURE_2D);

      stbi_image_free(buffer);
    }
  }

  /**
   * Bind the texture to be used during rendering.
   */
  @Override
  public void bind() {
    glBindTexture(GL_TEXTURE_2D, this.textureId);
  }

  /**
   * Unbind the texture from rendering to free resources.
   */
  @Override
  public void unbind() {
    glDeleteTextures(this.textureId);
  }
}
