package com.github.freddyyj.randomtsw.config;

import com.github.freddyyj.randomtrainsimworld2.config.SaveLoco;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SaveLocoTest {
    private static SaveLoco saveLoco;
    @BeforeAll
    public static void createSaveLoco(){
        ArrayList<String> routeList=new ArrayList<>();
        routeList.add("route1");
        routeList.add("route2");
        routeList.add("route3");

        saveLoco=new SaveLoco(routeList);
    }

    @Test
    public void routeTest(){
        saveLoco.addRoute("testRoute");
        Assertions.assertTrue(saveLoco.getRoute().contains("testRoute"));

        saveLoco.removeRoute("testRoute");
        Assertions.assertFalse(saveLoco.getRoute().contains("testRoute"));
    }

    @Test
    public void locomotiveTest(){
        saveLoco.addLocomotive("route1","testLoco");
        Assertions.assertTrue(saveLoco.getLocomotive("route1").contains("testLoco"));

        saveLoco.removeLocomotive("route1","testLoco");
        Assertions.assertFalse(saveLoco.getLocomotive("route1").contains("testLoco"));
    }
    @Test
    public void weatherTest(){
        saveLoco.addWeather("testWeather");
        Assertions.assertTrue(saveLoco.getWeather().contains("testWeather"));

        saveLoco.removeWeather("testWeather");
        Assertions.assertFalse(saveLoco.getWeather().contains("testWeather"));
    }
}
