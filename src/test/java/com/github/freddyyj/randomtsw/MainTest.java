package com.github.freddyyj.randomtsw;

import com.github.freddyyj.randomtrainsimworld2.Locomotive;
import com.github.freddyyj.randomtrainsimworld2.Main;
import com.github.freddyyj.randomtrainsimworld2.Route;
import com.github.freddyyj.randomtrainsimworld2.Weather;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MainTest {
    private static Main main;
    @BeforeAll
    public static void createMain(){
        try {
            main=Main.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Test getters
    @Test
    public void getRouteTest(){
        ArrayList<Route> routes=main.getRoutes();
        Assertions.assertTrue(routes.size()>0);

        Assertions.assertNotNull(main.getRoute("Sand Patch Grade"));
        Assertions.assertNull(main.getRoute("Some invalid route"));
    }
    @Test
    public void getLocomotiveTest(){
        Map<Route,ArrayList<Locomotive>> locomotives=main.getLocos();
        Assertions.assertTrue(locomotives.size()>0);

        Assertions.assertTrue(main.getLocomotive("Sand Patch Grade").size()>0);
        Assertions.assertNull(main.getLocomotive("Some invalid route"));

        Assertions.assertNotNull(main.getLocomotive("Sand Patch Grade","GP38-2 CSX"));
        Assertions.assertNull(main.getLocomotive("Sand Patch Grade","Some invalid locomotive"));
    }
    @Test
    public void getWeatherTest(){
        ArrayList<Weather> weathers=main.getWeathers();
        Assertions.assertTrue(weathers.size()>0);

        Assertions.assertNotNull(main.getWeather("Spring Clear"));
        Assertions.assertNull(main.getWeather("Some invalid weather"));
    }

    // Test selects
    @Test
    public void selectRouteTest(){
        Route route=main.getRoute("Sand Patch Grade");

        main.selectRoute(false,route);
        Assertions.assertFalse(route.isSelected);

        main.selectRoute(true,route);
        Assertions.assertTrue(route.isSelected);
    }
    @Test
    public void selectLocoTest(){
        Locomotive loco= main.getLocomotive("Sand Patch Grade","GP38-2 CSX");
        Route route=main.getRoute("Sand Patch Grade");

        main.selectLocomotive(false,loco,route);
        Assertions.assertFalse(loco.isSelected);

        main.selectLocomotive(true,loco,route);
        Assertions.assertTrue(loco.isSelected);
    }
    @Test
    public void selectWeatherTest(){
        Weather weather=main.getWeather("Spring Clear");

        main.selectWeather(false,weather);
        Assertions.assertFalse(weather.isSelected);

        main.selectWeather(true,weather);
        Assertions.assertTrue(weather.isSelected);
    }

    // Test others
    @Test
    public void saveFilePathTest(){
        Assertions.assertNotNull(main.getSaveFilePath());
    }

    @AfterAll
    public static void close(){
        try {
            main.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
