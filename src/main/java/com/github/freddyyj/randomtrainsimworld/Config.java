package com.github.freddyyj.randomtrainsimworld;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Configuration class to store the inclusion/excludion data
 *
 * It loads and stores the excluded data.
 */
public class Config {
	private static final int VERSION = 2;

	// null if not saved or loaded yet, then nothing is excluded
	public final File configFile;
	// Excluded names read from the file, or written by the last save
	private final Set<String> excludedRoutes = new HashSet<>();
	// Route name -> locomotive names
	private final Map<String, Set<String>> excludedLocomotives = new HashMap<>();
	// Route name -> locomotive name -> livery names
	private final Map<String, Map<String, Set<String>>> excludedLiveries = new HashMap<>();
	// Route name -> journey names
	private final Map<String, Set<String>> excludedJourneys = new HashMap<>();
	// Route name -> scenario names
	private final Map<String, Set<String>> excludedScenarios = new HashMap<>();

	/**
	 * A constructor with the {@link File} of the configuration
	 * @param configFile File of the configuration
	 */
	public Config(final File configFile) {
		this.configFile = configFile;
	}

	/**
	 * A constructor with the path to the configuration file
	 * @param configFilePath Patch to the configuration file
	 */
	public Config(final String configFilePath) {
		this(new File(configFilePath));
	}

	/**
	 * A constructor with empty configuration (All included)
	 */
	public Config() {
		this((File) null);
	}

	/**
	 * Load a configuration fron the file
	 * @throws IOException Exception from the File I/O
	 */
	public void load() throws IOException {
		excludedRoutes.clear();
		excludedLocomotives.clear();
		excludedLiveries.clear();
		excludedJourneys.clear();
		excludedScenarios.clear();
		if (configFile == null || !configFile.exists()) return;

		try (Reader reader = new FileReader(configFile, StandardCharsets.UTF_8)) {
			final JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

			if (root.has("excludedRoutes")) {
				for (final JsonElement route : root.getAsJsonArray("excludedRoutes")) {
					excludedRoutes.add(route.getAsString());
				}
			}
			readNamesByRoute(root, "excludedLocomotives", excludedLocomotives);
			readNamesByRoute(root, "excludedJourneys", excludedJourneys);
			readNamesByRoute(root, "excludedScenarios", excludedScenarios);
			if (root.has("excludedLiveries")) {
				for (final Map.Entry<String, JsonElement> route : root.getAsJsonObject("excludedLiveries").entrySet()) {
					final Map<String, Set<String>> liveriesByLocomotive = new HashMap<>();
					for (final Map.Entry<String, JsonElement> locomotive : route.getValue().getAsJsonObject().entrySet()) {
						liveriesByLocomotive.put(locomotive.getKey(), readNames(locomotive.getValue().getAsJsonArray()));
					}
					excludedLiveries.put(route.getKey(), liveriesByLocomotive);
				}
			}
		} catch (JsonParseException | IllegalStateException | UnsupportedOperationException | ClassCastException e) {
			// Not a JSON file, or the structure is different
			throw new IOException("Invalid config file " + configFile.getPath(), e);
		}
	}

	/**
	 * Check the route is excluded
	 * @param route A {@link Route} object
	 * @return `true` if the route is excluded, `false` otherwise
	 */
	public boolean isRouteExcluded(final Route route) {
		return excludedRoutes.contains(route.name);
	}

	/**
	 * Check the locomotive is excluded in the route
	 * @param route A {@link Route} of the locomotive
	 * @param locomotive A {@link Locomotive} object
	 * @return `true` if the locomotive is excluded, `false` otherwise
	 */
	public boolean isLocomotiveExcluded(final Route route, final Locomotive locomotive) {
		return excludedLocomotives.getOrDefault(route.name, Set.of()).contains(locomotive.name);
	}

	/**
	 * Check the livery is excluded in the locomotive and route
	 * @param route A route of the locomotive
	 * @param locomotive A locomotive of the livery
	 * @param livery A livery, in string
	 * @return `true` if the livery is excluded, `false` otherwise
	 */
	public boolean isLiveryExcluded(final Route route, final Locomotive locomotive, final String livery) {
		return excludedLiveries.getOrDefault(route.name, Map.of()).getOrDefault(locomotive.name, Set.of()).contains(livery);
	}

	/**
	 * Check the journey is excluded in the route
	 * @param route A route
	 * @param journey A journey name in the route
	 * @return `true` if the journey is excluded, `false` otherwise
	 */
	public boolean isJourneyExcluded(final Route route, final String journey) {
		return excludedJourneys.getOrDefault(route.name, Set.of()).contains(journey);
	}

	/**
	 * Check the scenario is excluded in the route
	 * @param route A route
	 * @param scenario A {@link Scenario} in the route
	 * @return `true` if the scenario is excluded, `false` otherwise
	 */
	public boolean isScenarioExcluded(final Route route, final Scenario scenario) {
		return excludedScenarios.getOrDefault(route.name, Set.of()).contains(scenario.name);
	}

	/**
	 * Save the current change to the file
	 * @param main A {@link Main} object to get the updates
	 * @throws IOException I/O exception from file write
	 */
	public void save(final Main main) throws IOException {
		if (configFile == null) {
			throw new IllegalStateException("Config file path is not set");
		}
		excludedRoutes.clear();
		excludedLocomotives.clear();
		excludedLiveries.clear();
		excludedJourneys.clear();
		excludedScenarios.clear();
		for (final Route route : main.routes.values()) {
			if (!main.includedRoutes.contains(route)) excludedRoutes.add(route.name);
			for (final Map.Entry<Locomotive, ? extends List<String>> entry : route.locomotives.entrySet()) {
				final Locomotive locomotive = entry.getKey();
				if (!main.includedLocomotives.get(route).contains(locomotive)) {
					excludedLocomotives.computeIfAbsent(route.name, r -> new HashSet<>()).add(locomotive.name);
				}
				for (final String livery : entry.getValue()) {
					if (!main.includedLiveries.get(route).get(locomotive).contains(livery)) {
						excludedLiveries.computeIfAbsent(route.name, r -> new HashMap<>())
								.computeIfAbsent(locomotive.name, l -> new HashSet<>()).add(livery);
					}
				}
			}
			for (final String journey : route.journeys) {
				if (!main.includedJourneys.get(route).contains(journey)) {
					excludedJourneys.computeIfAbsent(route.name, r -> new HashSet<>()).add(journey);
				}
			}
			for (final Scenario scenario : route.scenarios) {
				if (!main.includedScenarios.get(route).contains(scenario)) {
					excludedScenarios.computeIfAbsent(route.name, r -> new HashSet<>()).add(scenario.name);
				}
			}
		}

		final JsonObject root = new JsonObject();
		root.addProperty("version", VERSION);
		root.add("excludedRoutes", toSortedArray(excludedRoutes));
		root.add("excludedLocomotives", namesByRouteToJson(excludedLocomotives));
		final JsonObject liveries = new JsonObject();
		for (final Map.Entry<String, Map<String, Set<String>>> route : new TreeMap<>(excludedLiveries).entrySet()) {
			liveries.add(route.getKey(), namesByRouteToJson(route.getValue()));
		}
		root.add("excludedLiveries", liveries);
		root.add("excludedJourneys", namesByRouteToJson(excludedJourneys));
		root.add("excludedScenarios", namesByRouteToJson(excludedScenarios));

		try (Writer writer = new FileWriter(configFile, StandardCharsets.UTF_8)) {
			writer.write(root.toString());
		}
	}

	/**
	 * Check the data is changed from the file
	 * @param main A {@link Main} object
	 * @return `true` if the data is different, `false` otherwise
	 */
	public boolean isChanged(final Main main) {
		for (final Route route : main.routes.values()) {
			if (main.includedRoutes.contains(route) == isRouteExcluded(route)) return true;
			for (final Map.Entry<Locomotive, ? extends List<String>> entry : route.locomotives.entrySet()) {
				final Locomotive locomotive = entry.getKey();
				if (main.includedLocomotives.get(route).contains(locomotive) == isLocomotiveExcluded(route, locomotive)) return true;
				for (final String livery : entry.getValue()) {
					if (main.includedLiveries.get(route).get(locomotive).contains(livery)
							== isLiveryExcluded(route, locomotive, livery)) return true;
				}
			}
			for (final String journey : route.journeys) {
				if (main.includedJourneys.get(route).contains(journey) == isJourneyExcluded(route, journey)) return true;
			}
			for (final Scenario scenario : route.scenarios) {
				if (main.includedScenarios.get(route).contains(scenario) == isScenarioExcluded(route, scenario)) return true;
			}
		}
		return false;
	}

	// Reads {"route name": ["name", ...], ...} into the given map
	private static void readNamesByRoute(final JsonObject root, final String key, final Map<String, Set<String>> target) {
		if (!root.has(key)) return;
		for (final Map.Entry<String, JsonElement> entry : root.getAsJsonObject(key).entrySet()) {
			target.put(entry.getKey(), readNames(entry.getValue().getAsJsonArray()));
		}
	}

	private static Set<String> readNames(final JsonArray array) {
		final Set<String> names = new HashSet<>();
		for (final JsonElement name : array) {
			names.add(name.getAsString());
		}
		return names;
	}

	// Writes {"key": ["name", ...], ...}, sorted so the file does not change without real changes
	private static JsonObject namesByRouteToJson(final Map<String, Set<String>> namesByKey) {
		final JsonObject json = new JsonObject();
		for (final Map.Entry<String, Set<String>> entry : new TreeMap<>(namesByKey).entrySet()) {
			json.add(entry.getKey(), toSortedArray(entry.getValue()));
		}
		return json;
	}

	private static JsonArray toSortedArray(final Set<String> names) {
		final JsonArray array = new JsonArray();
		for (final String name : new TreeSet<>(names)) {
			array.add(name);
		}
		return array;
	}
}
