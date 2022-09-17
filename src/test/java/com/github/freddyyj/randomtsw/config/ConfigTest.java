package com.github.freddyyj.randomtsw.config;

import com.github.freddyyj.randomtrainsimworld.config.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ConfigTest {
    private static Config config;

    @BeforeAll
    public static void createConfig() {
        try {
            config = new Config();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void configTest() {
        config.setConfig("testKey", "testValue");

        Assertions.assertEquals(config.getConfig("testKey"), "testValue");
    }
}