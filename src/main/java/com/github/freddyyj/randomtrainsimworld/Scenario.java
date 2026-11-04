package com.github.freddyyj.randomtrainsimworld;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Set;

public class Scenario {
    public final String name;
    public final int difficulty;
    public final String length;
    public final ImmutableSet<String> locomotives;

    public Scenario(final String name, final int difficulty, final String length, final Set<String> locomotives) {
        this.name = name;
        this.difficulty = difficulty;
        this.length = length;
        // Read-only copy so the caller's set cannot change it
        this.locomotives = ImmutableSet.copyOf(locomotives);
    }

    // Reads a scenario from an entry of the "scenario" object in a route JSON file
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
}
