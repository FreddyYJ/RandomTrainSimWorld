package com.github.freddyyj.randomtsw.config;

import com.github.freddyyj.randomtrainsimworld.Locomotive;
import com.github.freddyyj.randomtrainsimworld.Route;
import com.github.freddyyj.randomtrainsimworld.Weather;
import com.github.freddyyj.randomtrainsimworld.config.SaveLoco;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SaveLocoTest {
    private static SaveLoco saveLoco;
    private static ArrayList<Route> routeList;
    private static Locomotive locoForTest;
    private static Weather newWeather;
    @BeforeAll
    public static void createSaveLoco(){
        routeList=new ArrayList<>();
        routeList.add(new Route("name1","code1"));
        routeList.add(new Route("name2","code2"));
        routeList.add(new Route("name3","code3"));

        saveLoco=new SaveLoco(routeList);
        locoForTest=new Locomotive("loco1", routeList.get(0),false);
        newWeather=new Weather("weather1");
    }

    @Test
    @Order(1)
    public void hasSavefileTest(){
        Assertions.assertFalse(saveLoco.hasSavefile());
    }

    // Test getters
    @Test
    @Order(2)
    public void getRouteTest(){
        List<String> routes=saveLoco.getRoute();
        Assertions.assertEquals(routes.size(),0);
    }
    @Test
    @Order(2)
    public void getLocoTest(){
        List<String> locos=saveLoco.getLocomotive(routeList.get(0));
        Assertions.assertEquals(locos.size(),0);
    }
    @Test
    @Order(2)
    public void getWeatherTest(){
        List<String> weathers=saveLoco.getWeather();
        Assertions.assertEquals(weathers.size(),0);
    }

    // Test adders
    @Test
    @Order(3)
    public void addRouteTest(){
        saveLoco.addRoute(routeList.get(0));
        Assertions.assertEquals(saveLoco.getRoute().size(),1);
    }
    @Test
    @Order(3)
    public void addLocoTest(){
        saveLoco.addLocomotive(locoForTest);
        Assertions.assertEquals(saveLoco.getLocomotive(routeList.get(0)).size(),1);
    }
    @Test
    @Order(3)
    public void addWeatherTest(){
        saveLoco.addWeather(newWeather);
        Assertions.assertEquals(saveLoco.getWeather().size(),1);
    }

    // Test Remover
    @Test
    @Order(4)
    public void removeRouteTest(){
        saveLoco.removeRoute(routeList.get(0));
        Assertions.assertEquals(saveLoco.getRoute().size(),0);
    }
    @Test
    @Order(4)
    public void removeLocoTest() {
        saveLoco.removeLocomotive(locoForTest);
        Assertions.assertEquals(saveLoco.getLocomotive(routeList.get(0)).size(),0);
    }
    @Test
    @Order(4)
    public void removeWeatherTest(){
        saveLoco.removeWeather(newWeather);
        Assertions.assertEquals(saveLoco.getWeather().size(),0);
    }
}
