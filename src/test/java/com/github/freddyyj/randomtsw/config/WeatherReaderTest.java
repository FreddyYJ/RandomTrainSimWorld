package com.github.freddyyj.randomtsw.config;

import com.github.freddyyj.randomtrainsimworld2.Weather;
import com.github.freddyyj.randomtrainsimworld2.config.WeatherReader;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

public class WeatherReaderTest {
    private static List<String> reader;
    @BeforeAll
    public static void createReader(){
        try {
            WeatherReader.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader=WeatherReader.getWeathers();
    }
    @Test
    public void weatherTest(){
        Assertions.assertEquals(reader.get(0),"Spring Clear");
    }
}
