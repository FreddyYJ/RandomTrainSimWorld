package com.github.freddyyj.randomtrainsimworld;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {
    @Test
    public void testGetResourcePath() {
        // A file resource
        Path file = Utils.getResourcePath("/locomotives/usa/gp38-2.json");
        assertTrue(Files.isRegularFile(file));
        assertEquals("gp38-2.json", file.getFileName().toString());

        // A directory resource
        Path directory = Utils.getResourcePath("/routes");
        assertTrue(Files.isDirectory(directory));
        assertTrue(Files.exists(directory.resolve("usa/sand patch grade.json")));

        // Missing resource
        assertThrows(IllegalArgumentException.class, () -> Utils.getResourcePath("/not-exist.json"));
    }
}
