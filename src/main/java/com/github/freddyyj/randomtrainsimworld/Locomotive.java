package com.github.freddyyj.randomtrainsimworld;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * A locomotive class
 * @author FreddyYJ_
 */
public class Locomotive {
	public final String name;
	public final Nation nation;
	public final ImmutableList<String> liveries;
	public final String type;
	public final String manufacturer;
	public final String power;
	public final String manufacturedYear;
	public final String gauge;
	public final Integer totalProduced;
	public final String description;

	public Locomotive(final String name, final Nation nation, final List<String> liveries,
					  final String type, final String manufacturer, final String power,
					  final String manufacturedYear, final String gauge, final Integer totalProduced,
					  final String description) {
		this.name = name;
		this.nation = nation;
		// Read-only copy so the caller's list cannot change it
		this.liveries = ImmutableList.copyOf(liveries);
		this.type = type;
		this.manufacturer = manufacturer;
		this.power = power;
		this.manufacturedYear = manufacturedYear;
		this.gauge = gauge;
		this.totalProduced = totalProduced;
		this.description = description;
	}

	/**
	 * Reads a locomotive from a JSON file (see {@code resources/locomotives/gp38-2.json}).
	 * @param jsonFilePath path to the locomotive JSON file
	 */
	public Locomotive(final Path jsonFilePath) {
		final JsonObject json;
		try (Reader reader = Files.newBufferedReader(jsonFilePath, StandardCharsets.UTF_8)) {
			json = JsonParser.parseReader(reader).getAsJsonObject();
		} catch (IOException e) {
			throw new UncheckedIOException("Failed to read locomotive file " + jsonFilePath, e);
		}

		final ImmutableList.Builder<String> liveries = ImmutableList.builder();
		for (final JsonElement livery : json.getAsJsonArray("liveries")) {
			liveries.add(livery.getAsString());
		}

		this.name = json.get("name").getAsString();
		this.nation = Nation.fromString(json.get("nation").getAsString());
		this.liveries = liveries.build();
		this.type = json.get("type").getAsString();
		this.manufacturer = json.get("manufacturer").getAsString();
		this.power = json.get("power").getAsString();
		this.manufacturedYear = json.get("manufactured_year").getAsString();
		this.gauge = json.get("gauge").getAsString();
		this.totalProduced = json.get("total").getAsInt();
		this.description = json.get("description").getAsString();
	}

	/**
	 * Reads a bundled locomotive from {@code resources/locomotives/<locomotiveName>.json}.
	 * @param locomotiveName file name of the locomotive without {@code .json} (e.g. {@code "gp38-2"})
	 */
	public Locomotive(final String locomotiveName) {
		this(Utils.getResourcePath("/locomotives/" + locomotiveName + ".json"));
	}
}
