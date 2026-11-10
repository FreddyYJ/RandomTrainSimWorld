package com.github.freddyyj.randomtrainsimworld;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;

/**
 * Utilities, defined in static
 * @author FreddyYJ_
 */
public class Utils {
    /**
     * Converts a resource directory depends on the bundle
     *
     * Works whether the resource is a plain file (IDE/Gradle run),
     * inside a jar file ({@code jar:} URI), or inside a jlink/jpackage runtime image ({@code jrt:} URI).
     * @param resourceName absolute resource name (e.g. {@code "/locomotives/gp38-2.json"})
     * @return path to the resource
     * @throws IllegalArgumentException if the resource does not exist
     * @throws UncheckedIOException if the jar file system cannot be opened
     */
    public static Path getResourcePath(final String resourceName) {
        final URL url = Utils.class.getResource(resourceName);
        if (url == null) {
            throw new IllegalArgumentException("Resource " + resourceName + " not found.");
        }
        try {
            final URI uri = url.toURI();
            if ("jar".equals(uri.getScheme())) {
                // Path.of() cannot resolve a jar: URI until the jar's file system is opened.
                // It is intentionally left open so later lookups can reuse it.
                try {
                    FileSystems.newFileSystem(uri, Map.of());
                } catch (FileSystemAlreadyExistsException ignored) {
                }
            }
            return Path.of(uri);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid resource URI: " + url, e);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to open jar file system for " + url, e);
        }
    }
}
