package com.whyx.lwjgltest.engine.io.graphics.mesh;

import static org.lwjgl.opengl.GL33.*;

import com.whyx.lwjgltest.engine.io.IGameLogic;
import com.whyx.lwjgltest.engine.io.graphics.renderer.Renderer;
import com.whyx.lwjgltest.engine.io.graphics.vertex.Vertex;
import com.whyx.lwjgltest.engine.io.graphics.window.IWindow;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

/**
 * @author Samuel Wykes.
 * Represents a mesh consisting of multiple vertices and indices.
 */
public abstract class Mesh implements IGameLogic, IMesh {

  @NonNull
  @Getter
  private final List<Vertex> vertices;

  @NonNull
  @Getter
  private final List<Integer> indices;

  @NonNull
  private final Renderer renderer;

  private Integer vertexArrayObject;

  private Integer vertexBufferObject, colourBufferObject, indexBufferObject;

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

    final FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(this.vertices.size() * 3);
    this.vertices.stream()
        .map(Vertex::getPosition)
        .flatMap(position -> Stream.of(position.x, position.y, position.z))
        .forEach(vertexBuffer::put);
    vertexBuffer.flip();
    this.vertexBufferObject = this.storeFloatBuffer(vertexBuffer, 0, 3);

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

}
