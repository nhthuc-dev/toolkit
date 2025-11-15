package com.leratortech.toolkit.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;

public final class FileUtils {

    private FileUtils() {}

    /**
     * Checks whether the path exists.
     */
    public static boolean exists(Path path) {
        return path != null && Files.exists(path);
    }

    /**
     * Creates a directory if it does not exist.
     */
    public static Path createDirIfMissing(Path dir) throws IOException {
        if (dir == null) return null;
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
        return dir;
    }

    /**
     * Deletes file or directory (recursively for folders).
     */
    public static void deleteRecursive(Path path) throws IOException {
        if (path == null || !Files.exists(path)) return;

        if (Files.isDirectory(path)) {
            try (var stream = Files.list(path)) {
                for (Path p : stream.toList()) {
                    deleteRecursive(p);
                }
            }
        }
        Files.deleteIfExists(path);
    }

    /**
     * Reads all text from a file (UTF-8 by default).
     */
    public static String readString(Path file) throws IOException {
        return readString(file, Charset.forName("UTF-8"));
    }

    /**
     * Reads all text from file with specified charset.
     */
    public static String readString(Path file, Charset charset) throws IOException {
        return file == null ? null : Files.readString(file, charset);
    }

    /**
     * Writes string content to a file (UTF-8, auto-create parent directory).
     */
    public static void writeString(Path file, String content) throws IOException {
        writeString(file, content, Charset.forName("UTF-8"));
    }

    /**
     * Writes string to file with charset, autocreates directories.
     */
    public static void writeString(Path file, String content, Charset charset) throws IOException {
        if (file == null) return;
        createDirIfMissing(file.getParent());
        Files.writeString(file, content == null ? "" : content, charset, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Reads all lines from a file.
     */
    public static List<String> readLines(Path path) throws IOException {
        return path == null ? List.of() : Files.readAllLines(path);
    }

    /**
     * Copies a file, creating destination directory if needed.
     */
    public static void copy(Path source, Path target) throws IOException {
        if (source == null || target == null) return;
        createDirIfMissing(target.getParent());
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Moves a file, creating destination directory if needed.
     */
    public static void move(Path source, Path target) throws IOException {
        if (source == null || target == null) return;
        createDirIfMissing(target.getParent());
        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Gets file extension from filename (no dot).
     * Example: "test.png" -> "png"
     */
    public static String getExtension(String filename) {
        if (filename == null) return null;
        int index = filename.lastIndexOf('.');
        if (index <= 0 || index == filename.length() - 1) return "";
        return filename.substring(index + 1);
    }

    /**
     *
     * @param fileName
     * @return
     * Returns file name without extension.
     */
    public static String removeExtension(String fileName) {
        return fileName.replaceFirst("[.][^.]+$", "");
    }

    /**
     * Checks if filename matches extension.
     * Example: endsWith("abc.txt", "txt") -> true
     */
    public static boolean endsWithExtension(String filename, String ext) {
        if (filename == null || ext == null) return false;
        return filename.toLowerCase().endsWith("." + ext.toLowerCase());
    }
}
