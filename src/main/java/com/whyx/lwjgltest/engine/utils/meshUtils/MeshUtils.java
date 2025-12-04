package com.whyx.lwjgltest.engine.utils.meshUtils;

import static com.whyx.lwjgltest.engine.utils.FileUtils.loadAsString;

import com.whyx.lwjgltest.engine.io.graphics.mesh.IMesh;
import com.whyx.lwjgltest.engine.io.graphics.mesh.Mesh;
import com.whyx.lwjgltest.engine.io.graphics.texture.ITexture;
import com.whyx.lwjgltest.engine.io.graphics.texture.Texture;
import com.whyx.lwjgltest.engine.io.graphics.vertex.Vertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

/**
 * @author Samuel Wykes. Class used to load meshes from files.
 */
public class MeshUtils {

  /**
   * Load a mesh from an .obj file.
   *
   * @param modelPath {@link String} path to the .obj file.
   * @return {@link IMesh}.
   */
  public static IMesh loadMesh(
      final String modelPath,
      final String texturePath
  ) {
    final String objString = loadAsString(modelPath);

    final String[] objLines = objString.split("[\n|\r]");

    final ObjFile objFileContent = parseObjFile(objLines);

    return parseMeshFromObjFile(objFileContent, texturePath);
  }

  public static IMesh loadMesh(final String modelPath) {
    return loadMesh(modelPath, null);
  }

  private static ObjFile parseObjFile(final String[] objLines) {
    try {
      final ObjFile objFileContent = new ObjFile();
      for (final String line : objLines) {
        final String[] lineParts = line.split("\\s+");
        if (lineParts.length > 1) {
          switch (lineParts[0]) {
            case "v":
              if (lineParts.length == 4) {
                objFileContent.addVertex(new Vector3f(
                    Float.parseFloat(lineParts[1]),
                    Float.parseFloat(lineParts[2]),
                    Float.parseFloat(lineParts[3])
                ));
              }
              break;
            case "vn":
              if (lineParts.length == 4) {
                objFileContent.addNormal(new Vector3f(
                    Float.parseFloat(lineParts[1]),
                    Float.parseFloat(lineParts[2]),
                    Float.parseFloat(lineParts[3])
                ));
              }
              break;
            case "vt":
              if (lineParts.length == 3) {
                objFileContent.addTextureCoord(new Vector2f(
                        Float.parseFloat(lineParts[1]),
                        1 - Float.parseFloat(lineParts[2])
                    )
                );
              }
              break;
            case "f": {
              final ObjFace face = new ObjFace();
              for (int i = 1; i < lineParts.length; i++) {
                final String[] faceParts = lineParts[i].split("/");
                final int position = faceParts.length > 0 ? Integer.parseInt(faceParts[0]) : -1;
                final int texture = faceParts.length > 1 ? Integer.parseInt(faceParts[1]) : -1;
                final int normal = faceParts.length > 2 ? Integer.parseInt(faceParts[2]) : -1;
                face.addIndex(new Vector3i(
                    position,
                    texture,
                    normal
                ));
              }
              objFileContent.addFace(face);
              break;
            }
          }
        }
      }
      return objFileContent;

    } catch (final NumberFormatException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static IMesh parseMeshFromObjFile(
      final ObjFile objFileContent,
      final String texturePath
  ) {
    final List<Vector3i> uniqueVertices = objFileContent.getFaces()
        .stream().flatMap(face -> face.getVertexIndices().stream())
        .distinct()
        .toList();
    final List<Integer> indices = objFileContent.getFaces()
        .stream().flatMap(face -> face.getVertexIndices().stream())
        .map(uniqueVertices::indexOf)
        .toList();
    return Mesh.builder()
        .texture(texturePath == null ? null : new Texture(texturePath))
        .indices(indices)
        .vertices(
            uniqueVertices.stream()
                .map(vertexIndices -> Vertex.builder()
                    .position(objFileContent.getPositions().get(vertexIndices.x - 1))
                    .texture(vertexIndices.y > 0 ? objFileContent.getTextureCoords().get(vertexIndices.y - 1) : new Vector2f(0.0f, 0.0f))
                    .build()
                )
                .toList()
        )
        .build();
  }
}
