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

import com.whyx.lwjgltest.engine.io.IGameLogic;
import com.whyx.lwjgltest.engine.io.graphics.renderer.Renderer;
import com.whyx.lwjgltest.engine.io.graphics.vertex.Vertex;
import com.whyx.lwjgltest.engine.io.graphics.window.IWindow;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.NonNull;
import org.lwjgl.system.MemoryUtil;

/**
 * @author Samuel Wykes. Represents a mesh consisting of multiple vertices and indices.
 */
public abstract class Mesh implements IGameLogic, IMesh {

  @NonNull
  private final List<Vertex> vertices;

  @NonNull
  @Getter
  private final List<Integer> indices;

  @NonNull
  private final Renderer renderer;

  private Integer vertexArrayObject;

  private Integer vertexBufferObject, colourBufferObject, textureBufferObject, indexBufferObject;

  public Mesh(final List<Vertex> vertices, final List<Integer> indices, final Renderer renderer) {
    this.vertices = vertices;
    this.indices = indices;
    this.renderer = renderer;
    this.vertexArrayObject = null;
    this.vertexBufferObject = null;
    this.colourBufferObject = null;
    this.indexBufferObject = null;
  }

  @Override
  public void render(final IWindow window) {
    this.vertexArrayObject = glGenVertexArrays();
    glBindVertexArray(this.vertexArrayObject);

    int currentIndex = 0;

    final FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(this.vertices.size() * 3);
    this.vertices.stream()
        .map(Vertex::getPosition)
        .flatMap(position -> Stream.of(position.x, position.y, position.z))
        .forEach(vertexBuffer::put);
    vertexBuffer.flip();
    this.vertexBufferObject = this.storeFloatBuffer(vertexBuffer, currentIndex++, 3);

    final int colourCount = (int) this.getColourCount();
    final FloatBuffer colourBuffer = MemoryUtil.memAllocFloat(colourCount * 3);
    if (colourCount > 0) {
      this.vertices.stream()
          .map(Vertex::getColour)
          .filter(Optional::isPresent)
          .flatMap(colour -> Stream.of(colour.get().x, colour.get().y, colour.get().z))
          .forEach(colourBuffer::put);
      colourBuffer.flip();
      this.colourBufferObject = this.storeFloatBuffer(colourBuffer, currentIndex++, colourCount);
    }

    final int textureCount = (int) this.getTextureCount();
    final FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(textureCount * 3);
    if (textureCount > 0) {
      this.vertices.stream()
          .map(Vertex::getTexture)
          .filter(Optional::isPresent)
          .flatMap(colour -> Stream.of(colour.get().x, colour.get().y, colour.get().z))
          .forEach(textureBuffer::put);
      textureBuffer.flip();
      this.textureBufferObject = this.storeFloatBuffer(textureBuffer, currentIndex, textureCount);
    }

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

    if (colourCount > 0) {
      MemoryUtil.memFree(colourBuffer);
    }
    if (textureCount > 0) {
      MemoryUtil.memFree(textureBuffer);
    }

    this.renderer.renderMesh(this);
  }

  private int storeFloatBuffer(final FloatBuffer buffer, final int index, final int size) {
    final int bufferId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, bufferId);
    glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    return bufferId;
  }

  @Override
  public void cleanup() {
    glDeleteBuffers(this.vertexBufferObject);
    glDeleteBuffers(this.indexBufferObject);
    if (this.getColourCount() > 0)
      glDeleteBuffers(this.colourBufferObject);
    if (this.getTextureCount() > 0)
      glDeleteBuffers(this.textureBufferObject);

    glDeleteVertexArrays(this.vertexArrayObject);
  }


  @Override
  public int getVertexBufferObject() {
    return this.vertexBufferObject;
  }

  @Override
  public int getIndexBufferObject() {
    return this.indexBufferObject;
  }

  @Override
  public int getColourBufferObject() {
    return this.colourBufferObject;
  }

  @Override
  public long getVertexCount() {
    return this.vertices.size();
  }

  @Override
  public long getColourCount() {
    return this.vertices.stream()
        .map(Vertex::getColour)
        .filter(Optional::isPresent)
        .count();
  }

  @Override
  public long getTextureCount() {
    return this.vertices.stream()
        .map(Vertex::getTexture)
        .filter(Optional::isPresent)
        .count();
  }

}
