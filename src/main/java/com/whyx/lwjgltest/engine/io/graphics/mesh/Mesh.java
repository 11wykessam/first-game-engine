package com.whyx.lwjgltest.engine.io.graphics.mesh;

import static org.lwjgl.opengl.GL33.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL33.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL33.GL_FLOAT;
import static org.lwjgl.opengl.GL33.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL33.glBindBuffer;
import static org.lwjgl.opengl.GL33.glBindVertexArray;
import static org.lwjgl.opengl.GL33.glBufferData;
import static org.lwjgl.opengl.GL33.glDeleteBuffers;
import static org.lwjgl.opengl.GL33.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL33.glGenBuffers;
import static org.lwjgl.opengl.GL33.glGenVertexArrays;
import static org.lwjgl.opengl.GL33.glVertexAttribPointer;

import com.whyx.lwjgltest.engine.io.graphics.texture.ITexture;
import com.whyx.lwjgltest.engine.io.graphics.vertex.Vertex;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

/**
 * @author Samuel Wykes. Represents a mesh consisting of multiple vertices and indices.
 */
public class Mesh implements IMesh {

  private static final Integer POSITION_SIZE = 3;
  private static final Integer COLOUR_SIZE = 4;
  private static final Integer TEXTURE_SIZE = 2;

  @NonNull
  private final List<Vertex> vertices;

  @NonNull
  @Getter
  private final List<Integer> indices;

  private final ITexture texture;

  private Integer vertexArrayObject;

  @Getter
  private Integer vertexBufferObject, indexBufferObject;

  @Builder
  protected Mesh(
      final List<Vertex> vertices,
      final List<Integer> indices,
      final ITexture texture
  ) {
    this.vertices = vertices;
    this.indices = indices;
    this.texture = texture;
    this.vertexArrayObject = null;
    this.vertexBufferObject = null;
    this.indexBufferObject = null;
  }

  public void init() {
    this.vertexArrayObject = glGenVertexArrays();
    glBindVertexArray(this.vertexArrayObject);

    final int vertexSize = POSITION_SIZE + COLOUR_SIZE + TEXTURE_SIZE;
    final FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(this.vertices.size() * vertexSize);
    this.vertices.stream()
        .flatMap(vertex -> {
          final Vector3f position = vertex.getPosition();
          final Vector4f colour = vertex.getColour();
          final Vector2f texture = vertex.getTexture();
          return Stream.of(
              position.x, position.y, position.z,
              colour.x, colour.y, colour.z, colour.w,
              texture.x, texture.y
          );
        })
        .forEach(vertexBuffer::put);
    vertexBuffer.flip();

    this.vertexBufferObject = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, this.vertexBufferObject);
    glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

    this.storeFloatBuffer(
        0,
        POSITION_SIZE,
        vertexSize,
        0
    );
    this.storeFloatBuffer(
        1,
        COLOUR_SIZE,
        vertexSize,
        POSITION_SIZE
    );
    this.storeFloatBuffer(
        2,
        TEXTURE_SIZE,
        vertexSize,
        (POSITION_SIZE + COLOUR_SIZE)
    );

    glBindBuffer(GL_ARRAY_BUFFER, 0);

    final IntBuffer indicesBuffer = MemoryUtil.memAllocInt(this.indices.size());
    this.indices.forEach(indicesBuffer::put);
    indicesBuffer.flip();

    this.indexBufferObject = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.indexBufferObject);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

    // Free up buffers.
    MemoryUtil.memFree(vertexBuffer);
    MemoryUtil.memFree(indicesBuffer);
  }

  private void storeFloatBuffer(
      final int index,
      final int size,
      final int stride,
      final int pointer
  ) {
    glVertexAttribPointer(
        index,
        size,
        GL_FLOAT,
        false,
        stride * Float.BYTES,
        (long) pointer * Float.BYTES
    );
  }

  public void cleanup() {
    glDeleteBuffers(this.vertexBufferObject);
    glDeleteBuffers(this.indexBufferObject);

    glDeleteVertexArrays(this.vertexArrayObject);
  }

  @Override
  public int getIndexCount() {
    return this.indices.size();
  }

  @Override
  public long getVertexCount() {
    return this.vertices.size();
  }

  @Override
  public Optional<ITexture> getTexture() {
    return Optional.ofNullable(this.texture);
  }

}
