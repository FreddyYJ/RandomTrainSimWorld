package com.github.freddyyj.randomtrainsimworld;

import com.github.freddyyj.randomtrainsimworld.config.Config;
import com.google.common.collect.ImmutableList;
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
	public final ImmutableList<Route> routes;
	public final ImmutableList<Locomotive> locomotives;
	private Map<Route,Set<String>> selectedLocomotives;
	private Set<String> selectedRoutes;
	private Config config;
	private static Main core=null;

	public static void main(String[] args) {
		Main main = Main.getInstance();
		Application.launch(com.github.freddyyj.randomtrainsimworld.gui.Main.class);
	}

	public Main() {
		// Load every bundled route and locomotive file
		final ImmutableList.Builder<Route> routeBuilder = ImmutableList.builder();
		final ImmutableList.Builder<Locomotive> locomotiveBuilder = ImmutableList.builder();
		try (Stream<Path> routeFiles = Files.list(Utils.getResourcePath("/routes"));
			 Stream<Path> locomotiveFiles = Files.list(Utils.getResourcePath("/locomotives"))) {
			routeFiles.filter(path -> path.toString().endsWith(".json")).sorted()
					.forEach(path -> routeBuilder.add(new Route(path)));
			locomotiveFiles.filter(path -> path.toString().endsWith(".json")).sorted()
					.forEach(path -> locomotiveBuilder.add(new Locomotive(path)));
		} catch (IOException e) {
			throw new UncheckedIOException("Failed to list route or locomotive files", e);
		}
		routes = routeBuilder.build();
		locomotives = locomotiveBuilder.build();

		// Select everything at first
		selectedRoutes = new HashSet<>();
		selectedLocomotives = new HashMap<>();
		for (final Route route : routes) {
			selectedRoutes.add(route.name);
			final Set<String> routeLocomotives = new HashSet<>();
			for (final Locomotive locomotive : route.locomotives.keySet()) {
				routeLocomotives.add(locomotive.name);
			}
			selectedLocomotives.put(route, routeLocomotives);
		}

		try {
			config = new Config();
		} catch (Exception e) {

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

	/**
	 * Save and close save file.
	 */
	public void save() throws IOException {
		config.save();
	}

	/**
	 * Save savefile at specific path.
	 * @param path file path
	 */
	public void saveAs(String path) throws IOException {
		Config oldConfig = config;
		config = new Config(path, oldConfig);
		config.save();
	}

	/**
	 * Reload savefile from specific path.
	 * @param path file path
	 */
	public void reload(String path) throws IOException {
		config = new Config(path);
	}

	/**
	 * Get routes, locos or weathers selection are changed.
	 * @return true if selection changed
	 */
	public boolean isSaveChanged() {
		return config.isChanged();
	}
}
