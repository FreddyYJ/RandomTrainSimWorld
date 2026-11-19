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
 *
 * Each field is mostly used to generate a full description.
 * @author FreddyYJ_
 */
public class Route {
	public final String name;
	/**
	 * A map of the locomotive and its liveries
	 */
	public final ImmutableMap<Locomotive, ImmutableList<String>> locomotives;
	public final Nation nation;
	public final ImmutableList<String> journeys;
	public final ImmutableList<Scenario> scenarios;
	/**
	 * Description from the official Steam page of the route DLC
	 */
	public final String description;

	/**
	 * A default constructor with entire fields
	 */
	public Route(final String name, final Map<Locomotive,List<String>> locomotives, final Nation nation,
				 final List<String> journeys, final List<Scenario> scenarios, final String description) {
		this.name = name;
		this.locomotives = ImmutableMap.copyOf(Maps.transformValues(locomotives, ImmutableList::copyOf));
		this.nation = nation;
		this.journeys = ImmutableList.copyOf(journeys);
		this.scenarios = ImmutableList.copyOf(scenarios);
		this.description = description;
	}

	/**
	 * A constructor from the json file in resources
	 * @param jsonFilePath Path to json file
	 * @param loadedLocomotives Loaded locomotives from json files, to prevent the exception
	 */
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

	/**
	 * Get full description
	 * @return Full description
	 */
	public String getDescriptionString() {
		return "Name: " + name + "\n" +
				"Nation: " + nation + "\n" +
				"\n" + description + "\n";
	}
}
