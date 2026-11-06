package com.github.freddyyj.randomtrainsimworld;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SettingsTest {
    @Test
    public void testLoad() throws IOException {
        // Backup original setting if exist
        Path backup = Settings.FILE.toPath().resolveSibling("randomtsw-settings.json.bak");
        boolean backedUp = Settings.FILE.exists();
        if (backedUp)
            Files.copy(Settings.FILE.toPath(), backup, StandardCopyOption.REPLACE_EXISTING);

        try {
            // Write temp setting
            FileWriter writer = new FileWriter(Settings.FILE, StandardCharsets.UTF_8);
            writer.write("{\"configFilePath\": \"test-config.json\"}");
            writer.close();

            Settings setting = Settings.load();
            assertEquals("test-config.json", setting.configFilePath);
        } finally {
            // Revert, even if the test fails. Remove the temp setting if there was no original one
            if (backedUp) Files.move(backup, Settings.FILE.toPath(), StandardCopyOption.REPLACE_EXISTING);
            else Files.deleteIfExists(Settings.FILE.toPath());
        }
    }

    @Test
    public void testSave() throws IOException {
        // Backup original setting if exist
        Path backup = Settings.FILE.toPath().resolveSibling("randomtsw-settings.json.bak");
        boolean backedUp = Settings.FILE.exists();
        if (backedUp)
            Files.copy(Settings.FILE.toPath(), backup, StandardCopyOption.REPLACE_EXISTING);

        try {
            Settings setting = new Settings();
            setting.configFilePath = "test-config.json";
            setting.save();

            Settings new_setting = Settings.load();
            assertEquals("test-config.json", new_setting.configFilePath);
        } finally {
            // Revert, even if the test fails. Remove the temp setting if there was no original one
            if (backedUp) Files.move(backup, Settings.FILE.toPath(), StandardCopyOption.REPLACE_EXISTING);
            else Files.deleteIfExists(Settings.FILE.toPath());
        }
    }
}
