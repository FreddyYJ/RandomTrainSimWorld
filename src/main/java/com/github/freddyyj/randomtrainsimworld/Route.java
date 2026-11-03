package com.github.freddyyj.randomtrainsimworld;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
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
import java.util.Map;

/**
 * A route class
 * <p>
 *     Route name is a name of route.(ex. Sand Patch Grade)
 *
 *     Route code is a 2 to 4 length code of this route.(ex. SPG if this route is Sand Patch Grade)
 * </p>
 * @author FreddyYJ_
 */
public class Route {
	public final String name;
	public final ImmutableMap<Locomotive, ImmutableList<String>> locomotives;
	public final Nation nation;
	public final ImmutableList<String> journeys;
	public final String description;

	public Route(final String name, final Map<Locomotive,List<String>> locomotives, final Nation nation,
				 final List<String> journeys, final String description) {
		this.name = name;
		// Read-only copies, including each liveries list (ImmutableMap keeps the order)
		this.locomotives = ImmutableMap.copyOf(Maps.transformValues(locomotives, ImmutableList::copyOf));
		this.nation = nation;
		this.journeys = ImmutableList.copyOf(journeys);
		this.description = description;
	}

	// Reads a route from a JSON file (e.g. resources/routes/sand patch grade.json)
	public Route(final Path jsonFilePath) {
		final JsonObject json;
		try (Reader reader = Files.newBufferedReader(jsonFilePath, StandardCharsets.UTF_8)) {
			json = JsonParser.parseReader(reader).getAsJsonObject();
		} catch (IOException e) {
			throw new UncheckedIOException("Failed to read route file " + jsonFilePath, e);
		}

		// Each key is a locomotive file name, each value is the liveries used on this route
		// ImmutableMap keeps the order from the JSON file
		final ImmutableMap.Builder<Locomotive, ImmutableList<String>> locomotives = ImmutableMap.builder();
		for (final Map.Entry<String, JsonElement> entry : json.getAsJsonObject("locomotives").entrySet()) {
			final ImmutableList.Builder<String> liveries = ImmutableList.builder();
			for (final JsonElement livery : entry.getValue().getAsJsonArray()) {
				liveries.add(livery.getAsString());
			}
			locomotives.put(new Locomotive(entry.getKey()), liveries.build());
		}

		final ImmutableList.Builder<String> journeys = ImmutableList.builder();
		final JsonArray journeyArray = json.getAsJsonArray("journey");
		for (final JsonElement journey : journeyArray) {
			journeys.add(journey.getAsString());
		}

		this.name = json.get("name").getAsString();
		this.locomotives = locomotives.build();
		this.nation = Nation.fromString(json.get("nation").getAsString());
		this.journeys = journeys.build();
		this.description = json.get("description").getAsString();
	}

	// Reads a bundled route from resources/routes/<routeName>.json (e.g. "sand patch grade")
	public Route(final String routeName) {
		this(Utils.getResourcePath("/routes/" + routeName + ".json"));
	}
}
