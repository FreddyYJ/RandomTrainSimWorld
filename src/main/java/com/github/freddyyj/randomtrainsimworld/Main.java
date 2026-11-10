package com.github.freddyyj.randomtrainsimworld;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import javafx.application.Application;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * main class for control logics
 * This class should be Singleton. Use {@link Main#getInstance()} to get this object.
 * @author FreddyYJ_
 */
public class Main {
	/**
	 * All routes
	 */
	public final ImmutableMap<String, Route> routes;
	/**
	 * All locomotives
	 */
	public final ImmutableList<Locomotive> locomotives;

	private final Set<Route> routeInclusion = new HashSet<>();
	private final Map<Route,Set<Locomotive>> locomotiveInclusion = new HashMap<>();
	private final Map<Route,Map<Locomotive,Set<String>>> liveryInclusion = new HashMap<>();
	private final Map<Route,Set<String>> journeyInclusion = new HashMap<>();
	private final Map<Route,Set<Scenario>> scenarioInclusion = new HashMap<>();

	// Included data, to prevent modifying the data from outside
	public final Set<Route> includedRoutes = Collections.unmodifiableSet(routeInclusion);
	public final Map<Route,Set<Locomotive>> includedLocomotives;
	public final Map<Route,Map<Locomotive,Set<String>>> includedLiveries;
	public final Map<Route,Set<String>> includedJourneys;
	public final Map<Route,Set<Scenario>> includedScenarios;

	private Config config;
	private Settings settings;
	private static Main core=null;

	/**
	 * Main method, to launch the GUI
	 */
	public static void main(String[] args) {
		// Main is created in the GUI, so errors while loading data can be shown in an alert
		Application.launch(com.github.freddyyj.randomtrainsimworld.gui.Main.class);
	}

	/**
	 * Main constructor to initialize the data structure
	 */
	Main() {
		// Load every bundled route and locomotive file, including subdirectories (e.g. usa/)
		// Routes are keyed by their names (ImmutableMap keeps the file order)
		final ImmutableMap.Builder<String, Route> routeBuilder = ImmutableMap.builder();
		final ImmutableList.Builder<Locomotive> locomotiveBuilder = ImmutableList.builder();
		// One shared object per locomotive, keyed by its path under locomotives/ without .json (e.g. "usa/gp38-2")
		final Map<String, Locomotive> locomotivesByPath = new HashMap<>();
		final Path locomotiveDirectory = Utils.getResourcePath("/locomotives");
		try (Stream<Path> routeFiles = Files.walk(Utils.getResourcePath("/routes"));
			 Stream<Path> locomotiveFiles = Files.walk(locomotiveDirectory)) {
			// Locomotives first, since routes refer to them
			locomotiveFiles.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".json")).sorted()
					.forEach(path -> {
						final Locomotive locomotive = new Locomotive(path);
						locomotiveBuilder.add(locomotive);
						// Always use "/" as separator, same as route JSON keys
						final Path relativePath = locomotiveDirectory.relativize(path);
						final String key = relativePath.toString().replace(relativePath.getFileSystem().getSeparator(), "/");
						locomotivesByPath.put(key.substring(0, key.length() - ".json".length()), locomotive);
					});
			routeFiles.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".json")).sorted()
					.forEach(path -> {
						final Route route = new Route(path, locomotivesByPath);
						routeBuilder.put(route.name, route);
					});
		} catch (IOException e) {
			throw new UncheckedIOException("Failed to list route or locomotive files", e);
		}
		routes = routeBuilder.build();
		locomotives = locomotiveBuilder.build();

		// Include everything at first
		final ImmutableMap.Builder<Route, Set<Locomotive>> locomotiveViews = ImmutableMap.builder();
		final ImmutableMap.Builder<Route, Map<Locomotive, Set<String>>> liveryViews = ImmutableMap.builder();
		final ImmutableMap.Builder<Route, Set<String>> journeyViews = ImmutableMap.builder();
		final ImmutableMap.Builder<Route, Set<Scenario>> scenarioViews = ImmutableMap.builder();
		for (final Route route : routes.values()) {
			routeInclusion.add(route);
			final Set<Locomotive> routeLocomotives = new HashSet<>(route.locomotives.keySet());
			final Map<Locomotive, Set<String>> routeLiveries = new HashMap<>();
			final ImmutableMap.Builder<Locomotive, Set<String>> routeLiveryViews = ImmutableMap.builder();
			for (final Map.Entry<Locomotive, ImmutableList<String>> entry : route.locomotives.entrySet()) {
				// Liveries of this locomotive on this route
				final Set<String> liveries = new HashSet<>(entry.getValue());
				routeLiveries.put(entry.getKey(), liveries);
				routeLiveryViews.put(entry.getKey(), Collections.unmodifiableSet(liveries));
			}
			final Set<String> routeJourneys = new HashSet<>(route.journeys);
			final Set<Scenario> routeScenarios = new HashSet<>(route.scenarios);
			locomotiveInclusion.put(route, routeLocomotives);
			liveryInclusion.put(route, routeLiveries);
			journeyInclusion.put(route, routeJourneys);
			scenarioInclusion.put(route, routeScenarios);

			// Wrap the inner sets too, so nothing inside the views can be changed
			locomotiveViews.put(route, Collections.unmodifiableSet(routeLocomotives));
			liveryViews.put(route, routeLiveryViews.build());
			journeyViews.put(route, Collections.unmodifiableSet(routeJourneys));
			scenarioViews.put(route, Collections.unmodifiableSet(routeScenarios));
		}
		includedLocomotives = locomotiveViews.build();
		includedLiveries = liveryViews.build();
		includedJourneys = journeyViews.build();
		includedScenarios = scenarioViews.build();

		// Settings remember the config file of the last run
		try {
			settings = Settings.load(); // Creates the settings file at the first run
		} catch (IOException e) {
			System.err.println("Failed to read settings file: " + e.getMessage());
			settings = new Settings();
		}

		// Exclude what the last config file has, or keep everything included without one
		config = new Config();
		if (!settings.configFilePath.isEmpty()) {
			try {
				reload(settings.configFilePath);
			} catch (IOException e) {
				System.err.println("Failed to read config file: " + e.getMessage());
			}
		}
	}

	/**
	 * Get instance.
	 * @return instance of this class
	 */
	public static Main getInstance() {
		if (core==null) core=new Main();
		return core;
	}

	public void includeRoute(final Route route) {
		routeInclusion.add(route);
	}

	public void excludeRoute(final Route route) {
		routeInclusion.remove(route);
	}

	public void includeLocomotive(final Route route, final Locomotive locomotive) {
		locomotiveInclusion.get(route).add(locomotive);
	}

	public void excludeLocomotive(final Route route, final Locomotive locomotive) {
		locomotiveInclusion.get(route).remove(locomotive);
	}

	public void includeLivery(final Route route, final Locomotive locomotive, final String livery) {
		liveryInclusion.get(route).get(locomotive).add(livery);
	}

	public void excludeLivery(final Route route, final Locomotive locomotive, final String livery) {
		liveryInclusion.get(route).get(locomotive).remove(livery);
	}

	public void includeJourney(final Route route, final String journey) {
		journeyInclusion.get(route).add(journey);
	}

	public void excludeJourney(final Route route, final String journey) {
		journeyInclusion.get(route).remove(journey);
	}

	public void includeScenario(final Route route, final Scenario scenario) {
		scenarioInclusion.get(route).add(scenario);
	}

	public void excludeScenario(final Route route, final Scenario scenario) {
		scenarioInclusion.get(route).remove(scenario);
	}

	/**
	 * Save and close config file.
	 */
	public void save() throws IOException {
		config.save(this);
	}

	/**
	 * Save config at specific path.
	 * @param path file path
	 */
	public void saveAs(String path) throws IOException {
		final Config newConfig = new Config(path);
		newConfig.save(this);
		config = newConfig; // Save to this file from now on

		// Load this file at the next startup
		settings.configFilePath = path;
		settings.save();
	}

	/**
	 * Reload config file from specific path.
	 * @param path file path
	 */
	public void reload(String path) throws IOException {
		final Config newConfig = new Config(path);
		newConfig.load(); // Keep the current config if this fails
		config = newConfig;
		// Load this file at the next startup, skip writing if it is already there
		if (!path.equals(settings.configFilePath)) {
			settings.configFilePath = path;
			settings.save();
		}

		// Refill the inclusions in place, so the read-only views keep showing them
		for (final Route route : routes.values()) {
			if (config.isRouteExcluded(route)) routeInclusion.remove(route);
			else routeInclusion.add(route);

			final Set<Locomotive> locomotives = locomotiveInclusion.get(route);
			locomotives.clear();
			for (final Map.Entry<Locomotive, ImmutableList<String>> entry : route.locomotives.entrySet()) {
				final Locomotive locomotive = entry.getKey();
				if (!config.isLocomotiveExcluded(route, locomotive)) locomotives.add(locomotive);

				final Set<String> liveries = liveryInclusion.get(route).get(locomotive);
				liveries.clear();
				for (final String livery : entry.getValue()) {
					if (!config.isLiveryExcluded(route, locomotive, livery)) liveries.add(livery);
				}
			}

			final Set<String> journeys = journeyInclusion.get(route);
			journeys.clear();
			for (final String journey : route.journeys) {
				if (!config.isJourneyExcluded(route, journey)) journeys.add(journey);
			}

			final Set<Scenario> scenarios = scenarioInclusion.get(route);
			scenarios.clear();
			for (final Scenario scenario : route.scenarios) {
				if (!config.isScenarioExcluded(route, scenario)) scenarios.add(scenario);
			}
		}
	}

	/**
	 * Get whether included routes or locos are changed.
	 * @return true if inclusion changed
	 */
	public boolean isSaveChanged() {
		return config.isChanged(this);
	}

	/**
	 * Check the config is loaded from or stored to the file
	 * @return `true` if the config is loaded or stored, `false` otherwise
	 */
	public boolean hasConfigFile() {
		return config.configFile != null;
	}

	/**
	 * Exception that the route is not found by the name
	 */
	public static class RouteNotFoundException extends RuntimeException {
		public RouteNotFoundException(final String routeName) {
			super("Route " + routeName + " not found in the pool");
		}
	}
}
