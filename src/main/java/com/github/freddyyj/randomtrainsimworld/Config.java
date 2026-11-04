package com.github.freddyyj.randomtrainsimworld;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Config {
	// Config file in the system's document directory
	public static final File FILE_NAME = new File(
			FileSystemView.getFileSystemView().getDefaultDirectory(), "randomtsw.json");
	private final File configFile;
	private Set<String> ignoredRoutes;
	private Map<String, Set<String>> ignoredLocomotives;
	// Ignores stored in the config file, used to check changes
	private ImmutableSet<String> initialIgnoredRoutes;
	private ImmutableMap<String, Set<String>> initialIgnoredLocomotives;

	public Config(final File configFile) throws IOException{
		this.configFile = configFile;
		reloadConfig();
	}

	public Config(final String configFilePath) throws IOException {
		this(new File(configFilePath));
	}

	public Config() throws IOException {
		this(Config.FILE_NAME);
	}

	public Config(final File configFile, final Config oldConfig) throws IOException {
		// Read the file first to set initial ignores
		this(configFile);

		// Copy each set too, so changes here don't affect oldConfig
		this.ignoredRoutes = new HashSet<>(oldConfig.ignoredRoutes);
		this.ignoredLocomotives = new HashMap<>();
		for (Map.Entry<String, Set<String>> entry : oldConfig.ignoredLocomotives.entrySet()) {
			ignoredLocomotives.put(entry.getKey(), new HashSet<>(entry.getValue()));
		}
	}

	public Config(final String configFilePath, final Config oldConfig) throws IOException {
		this(new File(configFilePath), oldConfig);
	}

	public void addRouteIgnore(final String route) {
		ignoredRoutes.add(route);
	}

	public void addLocomotiveIgnore(final String route, final String locomotive) {
		if (!ignoredLocomotives.containsKey(route)) {
			// Add new route if not exist
			ignoredLocomotives.put(route, new HashSet<>());
		}
		ignoredLocomotives.get(route).add(locomotive);
	}

	public boolean isRouteIgnored(final String route) {
		return ignoredRoutes.contains(route);
	}

	public boolean isLocomotiveIgnored(final String route, final String locomotive) {
		if (ignoredRoutes.contains(route)) return true; // Route is ignored
		if (!ignoredLocomotives.containsKey(route)) return false; // Entire route is not ignored
		return ignoredLocomotives.get(route).contains(locomotive);
	}

	public void reloadConfig() throws IOException {
		ignoredRoutes = new HashSet<>();
		ignoredLocomotives = new HashMap<>();

		// No config file yet, keep it empty
		if (configFile.exists()) {
			if (!configFile.canRead()) {
				throw new IOException("Cannot read config file " + configFile.getPath());
			}

			try (Reader reader = new FileReader(configFile, StandardCharsets.UTF_8)) {
				JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

				// Routes ignored as a whole
				if (root.has("ignoredRoutes")) {
					for (JsonElement route : root.getAsJsonArray("ignoredRoutes")) {
						ignoredRoutes.add(route.getAsString());
					}
				}

				// Locomotives ignored in each route
				if (root.has("ignoredLocomotives")) {
					for (Map.Entry<String, JsonElement> entry : root.getAsJsonObject("ignoredLocomotives").entrySet()) {
						Set<String> locomotives = new HashSet<>();
						for (JsonElement locomotive : entry.getValue().getAsJsonArray()) {
							locomotives.add(locomotive.getAsString());
						}
						ignoredLocomotives.put(entry.getKey(), locomotives);
					}
				}
			}
		}

		// Remember what the file has
		initialIgnoredRoutes = ImmutableSet.copyOf(ignoredRoutes);
		initialIgnoredLocomotives = ImmutableMap.copyOf(Maps.transformValues(ignoredLocomotives, ImmutableSet::copyOf));
	}

	public void save() throws IOException {
		JsonObject root = new JsonObject();
		root.addProperty("version", 1);

		JsonArray routes = new JsonArray();
		for (String route : ignoredRoutes) {
			routes.add(route);
		}
		root.add("ignoredRoutes", routes);

		// Skip routes with no ignored locomotives
		JsonObject locomotivesByRoute = new JsonObject();
		for (Map.Entry<String, Set<String>> entry : ignoredLocomotives.entrySet()) {
			if (entry.getValue().isEmpty()) continue;
			JsonArray locomotives = new JsonArray();
			for (String locomotive : entry.getValue()) {
				locomotives.add(locomotive);
			}
			locomotivesByRoute.add(entry.getKey(), locomotives);
		}
		root.add("ignoredLocomotives", locomotivesByRoute);

		try (Writer writer = new FileWriter(configFile, StandardCharsets.UTF_8)) {
			writer.write(root.toString());
		}

		// The file now has the current ignores
		initialIgnoredRoutes = ImmutableSet.copyOf(ignoredRoutes);
		initialIgnoredLocomotives = ImmutableMap.copyOf(Maps.transformValues(ignoredLocomotives, ImmutableSet::copyOf));
	}

	public boolean isChanged() {
		if (!ignoredRoutes.equals(initialIgnoredRoutes)) return true;

		// Check both sides, an empty set is same as no entry
		for (Map.Entry<String, Set<String>> entry : ignoredLocomotives.entrySet()) {
			if (!entry.getValue().equals(initialIgnoredLocomotives.getOrDefault(entry.getKey(), Set.of()))) return true;
		}
		for (Map.Entry<String, Set<String>> entry : initialIgnoredLocomotives.entrySet()) {
			if (!entry.getValue().equals(ignoredLocomotives.getOrDefault(entry.getKey(), Set.of()))) return true;
		}
		return false;
	}
}
