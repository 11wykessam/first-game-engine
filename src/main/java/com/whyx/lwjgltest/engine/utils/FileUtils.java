package com.whyx.lwjgltest.engine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Samuel Wykes. Utils for reading/writing to files.
 */
public class FileUtils {

  /**
   * Load a file as a string from resources.
   *
   * @param path {@link String} path to a file in resources folder.
   * @return {@link String} file contents.
   */
  public static String loadAsString(final String path) {
    final StringBuilder builder = new StringBuilder();
    try (final BufferedReader reader = new BufferedReader(
        new InputStreamReader(FileUtils.class.getResourceAsStream(path)))) {
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line).append("\n");
      }
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
    return builder.toString();

  }

}
