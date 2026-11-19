package com.github.freddyyj.randomtrainsimworld;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Settings of the Random TSW
 */
public class Settings {
	/**
	 * Document directory of this system
	 */
	public static final File DOCUMENT_DIRECTORY = FileSystemView.getFileSystemView().getDefaultDirectory();
	/**
	 * {@link File} of the setting file, in document directory
	 */
	public static final File FILE = new File(DOCUMENT_DIRECTORY, "randomtsw-settings.json");

	/**
	 * Path to the configuration file
	 */
	public String configFilePath = "";

	/**
	 * Load setting from the file
	 * @return {@link Settings} object
	 * @throws IOException File read exception
	 */
	public static Settings load() throws IOException {
		final Settings settings = new Settings();
		if (!FILE.exists()) {
			settings.save();
			return settings;
		}

		try (Reader reader = new FileReader(FILE, StandardCharsets.UTF_8)) {
			final JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
			if (root.has("configFilePath")) {
				settings.configFilePath = root.get("configFilePath").getAsString();
			}
		} catch (JsonParseException | IllegalStateException | UnsupportedOperationException e) {
			// Not a JSON file, or the structure is different
			throw new IOException("Invalid settings file " + FILE.getPath(), e);
		}
		return settings;
	}

	/**
	 * Save setting to the file
	 * @throws IOException File write exception
	 */
	public void save() throws IOException {
		final JsonObject root = new JsonObject();
		root.addProperty("configFilePath", configFilePath);
		try (Writer writer = new FileWriter(FILE, StandardCharsets.UTF_8)) {
			writer.write(root.toString());
		}
	}
}
