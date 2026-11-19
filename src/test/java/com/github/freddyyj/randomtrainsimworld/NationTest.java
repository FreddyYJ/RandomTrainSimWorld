package com.github.freddyyj.randomtrainsimworld;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NationTest {
    @Test
    public void testToString() {
        Nation nation = Nation.USA;
        assertEquals("USA", nation.toString());
        assertEquals("Swiss", Nation.Switzerland.toString());
    }
    
    @Test
    public void testFromString() {
        Nation nation = Nation.fromString("Germany");
        assertEquals(Nation.Germany, nation);
        assertEquals(Nation.Switzerland, Nation.fromString("Switzerland"));
    }
    
    @Test
    public void testNationNotFoundException() {
        assertThrowsExactly(Nation.NationNotFoundException.class, () -> Nation.fromString("TerribleNation"));
        assertThrowsExactly(Nation.NationNotFoundException.class, () -> Nation.fromString(null));
    }
}
