package br.ufal;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class FilenameValidator {

  // Regex pattern for invalid characters in filenames
  private static final Pattern INVALID_FILENAME_PATTERN = Pattern.compile("[<>:\"\\|?*]");

  public static boolean isValidFilename(String filename) {
    if (filename == null || filename.trim().isEmpty()) {
      return false;
    }
    if (INVALID_FILENAME_PATTERN.matcher(filename).find()) {
      return false;
    }
    if (filename.length() > 255) {
      return false;
    }

    try {
      Paths.get(filename);
    } catch (InvalidPathException e) {
      return false;
    }
    return true;
  }
}
