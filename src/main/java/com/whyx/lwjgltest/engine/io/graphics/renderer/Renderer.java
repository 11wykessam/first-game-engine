package com.whyx.lwjgltest.engine.io.graphics.renderer;

import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.PROJECTION_MATRIX_UNIFORM;
import static com.whyx.lwjgltest.engine.constants.GameEngineConstants.WORLD_MATRIX_UNIFORM;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import com.whyx.lwjgltest.engine.io.graphics.camera.ICamera;
import com.whyx.lwjgltest.engine.io.graphics.entity.IGameEntity;
import com.whyx.lwjgltest.engine.io.graphics.mesh.IMesh;
import com.whyx.lwjgltest.engine.io.graphics.mesh.Mesh;
import com.whyx.lwjgltest.engine.io.graphics.shader.Shader;
import lombok.Builder;
import lombok.NonNull;
import org.lwjgl.opengl.GL33;

/**
 * @author Samuel Wykes.
 * Renderer for the game. Can render invidual {@link Mesh} objects.
 */
public class Renderer implements IRenderer {

  @NonNull
  private final Shader shader;

  @Builder
  private Renderer(final Shader shader) {
    this.shader = shader;
  }

  @Override
  public void renderEntity(final IGameEntity entity, final ICamera camera) {
    final IMesh mesh = entity.getMesh();
    mesh.init();
    this.shader.bind();
    this.shader.setUniformMatrix4f(PROJECTION_MATRIX_UNIFORM, camera.getProjectionMatrix());
    this.shader.setUniformMatrix4f(WORLD_MATRIX_UNIFORM, entity.getWorldMatrix());
    this.renderMesh(mesh);
    this.shader.unbind();
  }

  /**
   * Renders a mesh.
   * @param mesh {@link Mesh} to render.
   */
  private void renderMesh(final IMesh mesh) {
    glBindVertexArray(mesh.getVertexBufferObject());
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIndexBufferObject());

    glDrawElements(GL_TRIANGLES, mesh.getIndexCount(), GL33.GL_UNSIGNED_INT, 0);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);
    glBindVertexArray(0);
  }
}
