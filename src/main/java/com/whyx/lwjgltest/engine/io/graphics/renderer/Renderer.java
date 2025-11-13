package com.whyx.lwjgltest.engine.io.graphics.renderer;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import com.whyx.lwjgltest.engine.io.graphics.mesh.IMesh;
import com.whyx.lwjgltest.engine.io.graphics.mesh.Mesh;
import com.whyx.lwjgltest.engine.io.graphics.shader.Shader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.lwjgl.opengl.GL33;

/**
 * @author Samuel Wykes.
 * Renderer for the game. Can render invidual {@link Mesh} objects.
 */
@AllArgsConstructor
public class Renderer implements IRenderer {

  private final Shader shader;

  /**
   * Renders a mesh.
   * @param mesh {@link Mesh} to render.
   */
  @Override
  public void renderMesh(final IMesh mesh) {
    final int vertexCount =  (int) mesh.getVertexCount();
    final int colourCount =  (int) mesh.getColourCount();
    final int textureCount =  (int) mesh.getTextureCount();

    int currentIndex = 0;

    glBindVertexArray(mesh.getVertexBufferObject());
    glEnableVertexAttribArray(currentIndex++);
    if (colourCount > 0)
      glEnableVertexAttribArray(currentIndex++);
    if (textureCount > 0)
      glEnableVertexAttribArray(currentIndex);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIndexBufferObject());

    if (this.shader != null)
      this.shader.bind();
    glDrawElements(GL_TRIANGLES, (int) mesh.getVertexCount(), GL33.GL_UNSIGNED_INT, 0);
    if (this.shader != null)
      this.shader.unbind();

    currentIndex = 0;
    glDisableVertexAttribArray(currentIndex);
    if(colourCount > 0)
      glDisableVertexAttribArray(currentIndex++);
    if(textureCount > 0)
      glDisableVertexAttribArray(currentIndex);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    glBindVertexArray(0);
  }
}
