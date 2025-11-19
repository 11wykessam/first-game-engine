package com.whyx.lwjgltest.game.cubeTest;

import com.whyx.lwjgltest.engine.io.graphics.mesh.Mesh;
import com.whyx.lwjgltest.engine.io.graphics.texture.ITexture;
import com.whyx.lwjgltest.engine.io.graphics.vertex.Vertex;
import java.util.List;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * @author Samuel Wykes.
 */
public class CubeMesh extends Mesh {

  private final static List<Vertex> vertices = List.of(
      Vertex.builder().position(new Vector3f(-0.5f, 0.5f, 0.5f)).texture(new Vector2f(0.0f, 0.0f))
          .build(),
      Vertex.builder().position(new Vector3f(-0.5f, -0.5f, 0.5f)).texture(new Vector2f(0.0f, 0.5f))
          .build(),
      Vertex.builder().position(new Vector3f(0.5f, -0.5f, 0.5f)).texture(new Vector2f(0.5f, 0.5f))
          .build(),
      Vertex.builder().position(new Vector3f(0.5f, 0.5f, 0.5f)).texture(new Vector2f(0.5f, 0.0f))
          .build(),

      Vertex.builder().position(new Vector3f(-0.5f, 0.5f, -0.5f)).texture(new Vector2f(0.0f, 0.0f))
          .build(),
      Vertex.builder().position(new Vector3f(0.5f, 0.5f, -0.5f)).texture(new Vector2f(0.5f, 0.0f))
          .build(),
      Vertex.builder().position(new Vector3f(-0.5f, -0.5f, -0.5f)).texture(new Vector2f(0.0f, 0.5f))
          .build(),
      Vertex.builder().position(new Vector3f(0.5f, -0.5f, -0.5f)).texture(new Vector2f(0.5f, 0.5f))
          .build(),

      Vertex.builder().position(new Vector3f(-0.5f, 0.5f, -0.5f)).texture(new Vector2f(0.0f, 0.5f))
          .build(),
      Vertex.builder().position(new Vector3f(0.5f, 0.5f, -0.5f)).texture(new Vector2f(0.5f, 0.5f))
          .build(),
      Vertex.builder().position(new Vector3f(-0.5f, 0.5f, 0.5f)).texture(new Vector2f(0.0f, 1.0f))
          .build(),
      Vertex.builder().position(new Vector3f(0.5f, 0.5f, 0.5f)).texture(new Vector2f(0.5f, 1.0f))
          .build(),

      Vertex.builder().position(new Vector3f(0.5f, 0.5f, 0.5f)).texture(new Vector2f(0.0f, 0.0f))
          .build(),
      Vertex.builder().position(new Vector3f(0.5f, -0.5f, 0.5f)).texture(new Vector2f(0.0f, 0.5f))
          .build(),

      Vertex.builder().position(new Vector3f(-0.5f, 0.5f, 0.5f)).texture(new Vector2f(0.5f, 0.0f))
          .build(),
      Vertex.builder().position(new Vector3f(-0.5f, -0.5f, 0.5f)).texture(new Vector2f(0.5f, 0.5f))
          .build(),

      Vertex.builder().position(new Vector3f(-0.5f, -0.5f, -0.5f)).texture(new Vector2f(0.5f, 0.0f))
          .build(),
      Vertex.builder().position(new Vector3f(0.5f, -0.5f, -0.5f)).texture(new Vector2f(1.0f, 0.0f))
          .build(),
      Vertex.builder().position(new Vector3f(-0.5f, -0.5f, 0.5f)).texture(new Vector2f(0.5f, 0.5f))
          .build(),
      Vertex.builder().position(new Vector3f(0.5f, -0.5f, 0.5f)).texture(new Vector2f(1.0f, 0.5f))
          .build()
  );

  private final static List<Integer> indices = List.of(
      // Front face
      0, 1, 3, 3, 1, 2,

      // Top face
      8, 10, 11, 9, 8, 11,

      // Right face
      12, 13, 7, 5, 12, 7,

      // Left face
      14, 15, 6, 4, 14, 6,

      // Bottom face
      16, 18, 19, 17, 16, 19,

      // Back face
      4, 6, 7, 5, 4, 7
  );

  public CubeMesh(final ITexture texture) {

    super(vertices, indices, texture);
  }

}
