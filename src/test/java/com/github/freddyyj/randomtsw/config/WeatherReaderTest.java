package com.github.freddyyj.randomtsw.config;

import com.github.freddyyj.randomtrainsimworld2.Weather;
import com.github.freddyyj.randomtrainsimworld2.config.WeatherReader;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

public class WeatherReaderTest {
    @BeforeAll
    public static void createReader(){
        try {
            WeatherReader.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void weatherTest(){
        List<Weather> weathers=WeatherReader.getWeathers();
        Assertions.assertEquals(weathers.get(0).getName(),"Spring Clear");
    }
}
