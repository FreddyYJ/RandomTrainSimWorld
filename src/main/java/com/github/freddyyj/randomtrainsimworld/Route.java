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
	public final ImmutableList<Scenario> scenarios;
	public final String description;

	public Route(final String name, final Map<Locomotive,List<String>> locomotives, final Nation nation,
				 final List<String> journeys, final List<Scenario> scenarios, final String description) {
		this.name = name;
		// Read-only copies, including each liveries list (ImmutableMap keeps the order)
		this.locomotives = ImmutableMap.copyOf(Maps.transformValues(locomotives, ImmutableList::copyOf));
		this.nation = nation;
		this.journeys = ImmutableList.copyOf(journeys);
		this.scenarios = ImmutableList.copyOf(scenarios);
		this.description = description;
	}

	// Reads a route from a JSON file (e.g. resources/routes/usa/sand patch grade.json)
	public Route(final Path jsonFilePath, final Map<String, Locomotive> loadedLocomotives) {
		final JsonObject json;
		try (Reader reader = Files.newBufferedReader(jsonFilePath, StandardCharsets.UTF_8)) {
			json = JsonParser.parseReader(reader).getAsJsonObject();
		} catch (IOException e) {
			throw new UncheckedIOException("Failed to read route file " + jsonFilePath, e);
		}

		final ImmutableMap.Builder<Locomotive, ImmutableList<String>> locomotives = ImmutableMap.builder();
		for (final Map.Entry<String, JsonElement> entry : json.getAsJsonObject("locomotives").entrySet()) {
			final ImmutableList.Builder<String> liveries = ImmutableList.builder();
			for (final JsonElement livery : entry.getValue().getAsJsonArray()) {
				liveries.add(livery.getAsString());
			}
			final Locomotive locomotive = loadedLocomotives.get(entry.getKey());
			if (locomotive == null) {
				throw new IllegalArgumentException("Unknown locomotive " + entry.getKey() + " in route file " + jsonFilePath);
			}
			locomotives.put(locomotive, liveries.build());
		}

		final ImmutableList.Builder<String> journeys = ImmutableList.builder();
		final JsonArray journeyArray = json.getAsJsonArray("journey");
		for (final JsonElement journey : journeyArray) {
			journeys.add(journey.getAsString());
		}

		// Scenarios are keyed by their names in the "scenario" object
		final ImmutableList.Builder<Scenario> scenarios = ImmutableList.builder();
		for (final Map.Entry<String, JsonElement> entry : json.getAsJsonObject("scenario").entrySet()) {
			scenarios.add(new Scenario(entry.getKey(), entry.getValue().getAsJsonObject()));
		}

		this.name = json.get("name").getAsString();
		this.locomotives = locomotives.build();
		this.nation = Nation.fromString(json.get("nation").getAsString());
		this.journeys = journeys.build();
		this.scenarios = scenarios.build();
		this.description = json.get("description").getAsString();
	}
}
