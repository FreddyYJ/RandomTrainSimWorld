package com.github.freddyyj.randomtrainsimworld;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {
    // Route and locomotives from the JSON files, created again for each test
    private final Locomotive gp38 = new Locomotive(FileSystems.getDefault().getPath("src/main/resources/locomotives/usa/gp38-2.json"));
    private final Locomotive sd402 = new Locomotive(FileSystems.getDefault().getPath("src/main/resources/locomotives/usa/sd40-2.json"));
    private final Route route = new Route(FileSystems.getDefault().getPath("src/main/resources/routes/usa/sand patch grade.json"),
            Map.of("usa/gp38-2", gp38,
                    "usa/sd40-2", sd402,
                    "usa/ac4400cw", new Locomotive(FileSystems.getDefault().getPath("src/main/resources/locomotives/usa/ac4400cw.json")),
                    "usa/c40-8w", new Locomotive(FileSystems.getDefault().getPath("src/main/resources/locomotives/usa/c40-8w.json")),
                    "usa/sd40", new Locomotive(FileSystems.getDefault().getPath("src/main/resources/locomotives/usa/sd40.json"))));

    // Excludes one item of each kind on Sand Patch Grade
    private static final String CONFIG_JSON = """
            {
              "version": 2,
              "excludedRoutes": ["Sand Patch Grade"],
              "excludedLocomotives": {"Sand Patch Grade": ["GP38-2"]},
              "excludedLiveries": {"Sand Patch Grade": {"SD40-2": ["BNSF"]}},
              "excludedJourneys": {"Sand Patch Grade": ["CSX C40-8W"]},
              "excludedScenarios": {"Sand Patch Grade": ["Stripey Rescue"]}
            }
            """;

    @Test
    public void testEmptyConfig(@TempDir Path tempDir) {
        // Config file in a temporary directory, not created yet
        Config config = new Config(tempDir.resolve("test-config.json").toFile());
        assertDoesNotThrow(config::load);

        // Nothing is excluded without a file
        assertFalse(config.isRouteExcluded(route));
        assertFalse(config.isLocomotiveExcluded(route, gp38));
        assertFalse(config.isLiveryExcluded(route, sd402, "BNSF"));
        assertFalse(config.isJourneyExcluded(route, "CSX C40-8W"));
        assertFalse(config.isScenarioExcluded(route, route.scenarios.getFirst()));
    }

    @Test
    public void testConstructors(@TempDir Path tempDir) {
        File file = tempDir.resolve("test-config.json").toFile();
        assertEquals(file, new Config(file).configFile);
        assertEquals(file, new Config(file.getPath()).configFile);
        assertNull(new Config().configFile);
    }

    @Test
    public void testLoad(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test-config.json");
        Files.writeString(file, CONFIG_JSON);
        Config config = new Config(file.toFile());
        assertDoesNotThrow(config::load);
        assertTrue(config.isRouteExcluded(route));

        // Loading again after the file is removed clears the old exclusions
        Files.delete(file);
        assertDoesNotThrow(config::load);
        assertFalse(config.isRouteExcluded(route));
        assertFalse(config.isLocomotiveExcluded(route, gp38));
    }

    @Test
    public void testLoadInvalidFile(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test-config.json");

        // Not a JSON file
        Files.writeString(file, "not a json");
        assertThrows(IOException.class, () -> new Config(file.toFile()).load());

        // JSON, but the structure is different
        Files.writeString(file, "{\"excludedRoutes\": {\"a\": 1}}");
        assertThrows(IOException.class, () -> new Config(file.toFile()).load());
    }

    @Test
    public void testLoadMissingKeys(@TempDir Path tempDir) throws IOException {
        // Keys not in the file mean nothing is excluded for them
        Path file = tempDir.resolve("test-config.json");
        Files.writeString(file, "{\"version\": 2}");
        Config config = new Config(file.toFile());
        assertDoesNotThrow(config::load);
        assertFalse(config.isRouteExcluded(route));
        assertFalse(config.isScenarioExcluded(route, route.scenarios.getFirst()));
    }

    @Test
    public void testIsRouteExcluded(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test-config.json");
        Files.writeString(file, CONFIG_JSON);
        Config config = new Config(file.toFile());
        config.load();

        assertTrue(config.isRouteExcluded(route));
    }

    @Test
    public void testIsLocomotiveExcluded(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test-config.json");
        Files.writeString(file, CONFIG_JSON);
        Config config = new Config(file.toFile());
        config.load();

        assertTrue(config.isLocomotiveExcluded(route, gp38));
        assertFalse(config.isLocomotiveExcluded(route, sd402));
    }

    @Test
    public void testIsLiveryExcluded(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test-config.json");
        Files.writeString(file, CONFIG_JSON);
        Config config = new Config(file.toFile());
        config.load();

        assertTrue(config.isLiveryExcluded(route, sd402, "BNSF"));
        assertFalse(config.isLiveryExcluded(route, sd402, "CSX"));
        // Same livery name on another locomotive is not excluded
        assertFalse(config.isLiveryExcluded(route, gp38, "BNSF"));
    }

    @Test
    public void testIsJourneyExcluded(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test-config.json");
        Files.writeString(file, CONFIG_JSON);
        Config config = new Config(file.toFile());
        config.load();

        assertTrue(config.isJourneyExcluded(route, "CSX C40-8W"));
        assertFalse(config.isJourneyExcluded(route, "Patch Work"));
    }

    @Test
    public void testIsScenarioExcluded(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test-config.json");
        Files.writeString(file, CONFIG_JSON);
        Config config = new Config(file.toFile());
        config.load();

        assertTrue(config.isScenarioExcluded(route, route.scenarios.getFirst()));
        assertFalse(config.isScenarioExcluded(route, route.scenarios.get(1)));
    }

    @Test
    public void testSaveWithoutFile() {
        Main main = new Main();
        assertThrows(IllegalStateException.class, () -> new Config().save(main));
    }

    @Test
    public void testSave(@TempDir Path tempDir) throws IOException {
        // Main may load the user's last config, so include everything on Sand Patch Grade first
        Main main = new Main();
        Route spg = main.routes.get("Sand Patch Grade");
        main.includeRoute(spg);
        for (Map.Entry<Locomotive, ? extends List<String>> entry : spg.locomotives.entrySet()) {
            main.includeLocomotive(spg, entry.getKey());
            for (String livery : entry.getValue()) main.includeLivery(spg, entry.getKey(), livery);
        }
        for (String journey : spg.journeys) main.includeJourney(spg, journey);
        for (Scenario scenario : spg.scenarios) main.includeScenario(spg, scenario);

        // Use the locomotives of main's route, matched by name
        Locomotive mainGp38 = spg.locomotives.keySet().stream().filter(l -> l.name.equals("GP38-2")).findFirst().orElseThrow();
        Locomotive mainSd402 = spg.locomotives.keySet().stream().filter(l -> l.name.equals("SD40-2")).findFirst().orElseThrow();
        main.excludeRoute(spg);
        main.excludeLocomotive(spg, mainGp38);
        main.excludeLivery(spg, mainSd402, "BNSF");
        main.excludeJourney(spg, "CSX C40-8W");
        main.excludeScenario(spg, spg.scenarios.getFirst());

        File file = tempDir.resolve("test-config.json").toFile();
        Config config = new Config(file);
        config.save(main);
        assertTrue(file.exists());

        // Saved exclusions are kept in this config
        assertTrue(config.isRouteExcluded(spg));
        assertTrue(config.isLocomotiveExcluded(spg, mainGp38));
        assertFalse(config.isLocomotiveExcluded(spg, mainSd402));

        // A new config reads the same exclusions from the file
        Config loaded = new Config(file);
        loaded.load();
        assertTrue(loaded.isRouteExcluded(route));
        assertTrue(loaded.isLocomotiveExcluded(route, gp38));
        assertFalse(loaded.isLocomotiveExcluded(route, sd402));
        assertTrue(loaded.isLiveryExcluded(route, sd402, "BNSF"));
        assertFalse(loaded.isLiveryExcluded(route, sd402, "CSX"));
        assertTrue(loaded.isJourneyExcluded(route, "CSX C40-8W"));
        assertFalse(loaded.isJourneyExcluded(route, "Patch Work"));
        assertTrue(loaded.isScenarioExcluded(route, route.scenarios.getFirst()));
        assertFalse(loaded.isScenarioExcluded(route, route.scenarios.get(1)));
    }

    @Test
    public void testIsChanged(@TempDir Path tempDir) throws IOException {
        Main main = new Main();
        Route spg = main.routes.get("Sand Patch Grade");
        Config config = new Config(tempDir.resolve("test-config.json").toFile());

        // Not changed right after saving
        config.save(main);
        assertFalse(config.isChanged(main));

        // Changed after including or excluding a journey, and not changed after undoing it
        String journey = spg.journeys.getFirst();
        boolean included = main.includedJourneys.get(spg).contains(journey);
        if (included) main.excludeJourney(spg, journey);
        else main.includeJourney(spg, journey);
        assertTrue(config.isChanged(main));
        if (included) main.includeJourney(spg, journey);
        else main.excludeJourney(spg, journey);
        assertFalse(config.isChanged(main));

        // Same for a route
        boolean routeIncluded = main.includedRoutes.contains(spg);
        if (routeIncluded) main.excludeRoute(spg);
        else main.includeRoute(spg);
        assertTrue(config.isChanged(main));
    }
}
