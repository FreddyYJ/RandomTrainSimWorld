package com.github.freddyyj.randomtrainsimworld;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Set;

/**
 * A scenario class
 *
 * Each information are from TSW in-game.
 */
public class Scenario {
    public final String name;
    /**
     * Difficulty, in 1 to 5
     */
    public final int difficulty;
    /**
     * Length of the scenario, in HH:MM format
     */
    public final String length;
    /**
     * Locomotive(s) used in this scenario
     */
    public final ImmutableSet<String> locomotives;

    /**
     * A default constructor with the full information
     */
    public Scenario(final String name, final int difficulty, final String length, final Set<String> locomotives) {
        this.name = name;
        this.difficulty = difficulty;
        this.length = length;
        // Read-only copy so the caller's set cannot change it
        this.locomotives = ImmutableSet.copyOf(locomotives);
    }

    /**
     * A constructor from the json object from a route
     * @param name Name of the scenario
     * @param json {@link JsonObject} from the route json file
     */
    public Scenario(final String name, final JsonObject json) {
        final ImmutableSet.Builder<String> locomotives = ImmutableSet.builder();
        for (final JsonElement locomotive : json.getAsJsonArray("locomotive")) {
            locomotives.add(locomotive.getAsString());
        }

        this.name = name;
        this.difficulty = json.get("difficulty").getAsInt();
        this.length = json.get("length").getAsString();
        this.locomotives = locomotives.build();
    }

    /**
     * Get full description
     * @return Full description
     */
    public String getDescriptionString() {
        return "Name: " + name + "\n" +
                "Difficulty: " + "█".repeat(difficulty) + "░".repeat(5 - difficulty) + "\n" +
                "Length: " + length + "\n" +
                "Locomotive Used: " + String.join(", ",locomotives) + "\n";
    }
}
