package com.github.freddyyj.randomtrainsimworld;

import org.junit.jupiter.api.Test;

import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RouteTest {
    @Test
    public void testRoute() {
        // Now we do not test basic constructor, since it takes lots of manual jobs
        // Test with json file instead
        // Load locomotives used in the route, keyed like the route JSON (e.g. "usa/gp38-2")
        Map<String, Locomotive> locomotives = new HashMap<>();
        for (String key : new String[]{"usa/gp38-2", "usa/sd40-2", "usa/ac4400cw", "usa/c40-8w", "usa/sd40"}) {
            locomotives.put(key, new Locomotive(FileSystems.getDefault().getPath("src/main/resources/locomotives/" + key + ".json")));
        }
        Route route = new Route(FileSystems.getDefault().getPath("src/main/resources/routes/usa/sand patch grade.json"), locomotives);

        assertEquals("Sand Patch Grade", route.name);
        assertEquals(Nation.USA, route.nation);
        assertEquals(5, route.locomotives.size());
        assertEquals(3, route.journeys.size());
        assertEquals(14, route.scenarios.size());

        assertEquals("CSX YN2", route.locomotives.get(locomotives.get("usa/gp38-2")).get(1));
        assertEquals("CSX C40-8W", route.journeys.get(1));
        assertEquals("Stripey Rescue", route.scenarios.getFirst().name);
    }

    @Test
    public void testDescription() {
        Map<String, Locomotive> locomotives = new HashMap<>();
        for (String key : new String[]{"usa/gp38-2", "usa/sd40-2", "usa/ac4400cw", "usa/c40-8w", "usa/sd40"}) {
            locomotives.put(key, new Locomotive(FileSystems.getDefault().getPath("src/main/resources/locomotives/" + key + ".json")));
        }
        Route route = new Route(FileSystems.getDefault().getPath("src/main/resources/routes/usa/sand patch grade.json"), locomotives);
        String desc = route.getDescriptionString();

        assertTrue(desc.contains("Name: Sand Patch Grade"));
        assertTrue(desc.contains("Nation: USA"));
    }
}
