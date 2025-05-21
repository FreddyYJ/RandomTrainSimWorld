package com.github.freddyyj.randomtsw;

import com.github.freddyyj.randomtrainsimworld.Main;
import org.junit.jupiter.api.*;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTest {
    private static Main core;
    @BeforeAll
    public static void init(){
        try {
            core=Main.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Test Getters
    @Test
    @Order(2)
    public void getRoutesTest() {
        Assertions.assertEquals(core.getRoutes().size(),71);
    }
    @Test
    @Order(2)
    public void getRouteTest(){
        Assertions.assertNotNull(core.getRoute("Sand Patch Grade"));
    }
    @Test
    @Order(2)
    public void getLocomotivesTest(){
        Assertions.assertEquals(core.getLocomotive("Sand Patch Grade").size(),5);
    }
    @Test
    @Order(2)
    public void getLocomotiveTest(){
        Assertions.assertNotNull(core.getLocomotive("Sand Patch Grade","AC4400CW CSX"));
    }
    @Test
    @Order(2)
    public void getWeathersTest(){
        Assertions.assertEquals(core.getWeathers().size(),17);
    }
    @Test
    @Order(2)
    public void getWeatherTest(){
        Assertions.assertNotNull(core.getWeather("Spring Rain"));
    }
}
