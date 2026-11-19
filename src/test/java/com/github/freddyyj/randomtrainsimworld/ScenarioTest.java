package com.github.freddyyj.randomtrainsimworld;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class ScenarioTest {
    @Test
    public void testScenario() {
        Set<String> locos = new HashSet<>();
        locos.add("a");
        locos.add("b");
        locos.add("c");
        Scenario s = new Scenario("temp name", 1, "1:00", locos);
        assertEquals("temp name", s.name);
        assertEquals(1, s.difficulty);
        assertEquals("1:00", s.length);
        assertTrue(s.locomotives.contains("a"));
        assertTrue(s.locomotives.contains("c"));
        assertFalse(s.locomotives.contains("f"));
    }

    @Test
    public void testScenarioExist() throws IOException {
        final Path path = Utils.getResourcePath("/routes/usa/sand patch grade.json");
        final JsonObject json;
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            json = JsonParser.parseReader(reader).getAsJsonObject();
        }

        // Scenarios are keyed by their names in the "scenario" object
        final Map<String, Scenario> scenarios = new HashMap<>();
        for (final Map.Entry<String, JsonElement> entry : json.getAsJsonObject("scenario").entrySet()) {
            scenarios.put(entry.getKey(), new Scenario(entry.getKey(), entry.getValue().getAsJsonObject()));
        }

        assertEquals(14, scenarios.size());

        final Scenario clearCut = scenarios.get("Clear Cut");
        assertNotNull(clearCut);
        assertEquals("Clear Cut", clearCut.name);
        assertEquals(2, clearCut.difficulty);
        assertEquals("0:45", clearCut.length);
        assertEquals(Set.of("SD40-2 CSX", "GP38-2 CSX", "AC4400CW CSX"), clearCut.locomotives);

        assertFalse(scenarios.containsKey("Terrible Scenario"));
    }

    @Test
    public void testGetDescription() {
        Set<String> locos = new HashSet<>();
        locos.add("a");
        locos.add("b");
        locos.add("c");
        Scenario s = new Scenario("temp name", 1, "1:00", locos);
        String desc = s.getDescriptionString();
        assertTrue(desc.contains("Name: temp name"));
        assertTrue(desc.contains("Difficulty: █░░░░"));
        assertTrue(desc.contains("Length: 1:00"));
        assertTrue(desc.contains("Locomotive Used: a, b, c"));
    }
}
