package com.github.freddyyj.randomtsw;

import com.github.freddyyj.randomtrainsimworld2.Locomotive;
import com.github.freddyyj.randomtrainsimworld2.Route;
import com.github.freddyyj.randomtrainsimworld2.Weather;
import org.junit.jupiter.api.Test;

public class DataObjectTest {
    @Test
    public void routeTest(){
        new Route("Foo","F");
    }
    @Test
    public void locomotiveTest(){
        Route route=new Route("Foo","F");
        new Locomotive("FooLoco",route);
    }
    @Test
    public void weatherTest(){
        new Weather("FooWeather");
    }
}
