package com.whyx.lwjgltest.engine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

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
    try (final InputStream stream = FileUtils.class.getResourceAsStream(path)) {
      if (stream == null) {
        throw new IllegalArgumentException("Resource not found: " + path);
      }
      return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

}
