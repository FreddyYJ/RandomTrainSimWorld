package com.github.freddyyj.randomtrainsimworld;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocomotiveTest {
    @Test
    public void testLocomotive() {
        List<String> liveries = new ArrayList<>();
        liveries.add("a");
        liveries.add("b");
        Locomotive l = new Locomotive("temp name", Nation.USA, liveries, "temp type", "temp company",
                "temp hp", "temp speed", "temp year", "temp gauge", 1,
                "temp desc");

        assertEquals("temp name", l.name);
        assertEquals(Nation.USA, l.nation);
        assertEquals("temp type", l.type);
        assertEquals("temp company", l.manufacturer);
        assertEquals("temp hp", l.power);
        assertEquals("temp speed", l.maxSpeed);
        assertEquals("temp year", l.manufacturedYear);
        assertEquals("temp gauge", l.gauge);
        assertEquals(1, l.totalProduced);
        assertEquals("temp desc", l.description);
    }

    @Test
    public void testLocomotiveJson() {
        Locomotive l = new Locomotive(FileSystems.getDefault().getPath("src/main/resources/locomotives/usa/gp38-2.json"));
        assertEquals("GP38-2", l.name);
        assertEquals(Nation.USA, l.nation);
        assertEquals("Diesel Electric Locomotive", l.type);
        assertEquals("Electro-Motive Diesel (EMD)", l.manufacturer);
        assertEquals("2,000 hp/1,490 kW", l.power);
        assertEquals("65 mph (105 km/h)", l.maxSpeed);
    }

    @Test
    public void testGetDescription() {
        Locomotive l = new Locomotive(FileSystems.getDefault().getPath("src/main/resources/locomotives/usa/gp38-2.json"));
        String desc = l.getDescriptionString();
        assertTrue(desc.contains("Name: GP38-2"));
        assertTrue(desc.contains("Manufacturer: Electro-Motive Diesel (EMD)"));
    }
}
