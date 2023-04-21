package com.github.freddyyj.randomtsw.config;

import com.github.freddyyj.randomtrainsimworld.Locomotive;
import com.github.freddyyj.randomtrainsimworld.Route;
import com.github.freddyyj.randomtrainsimworld.config.RouteReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class RouteReaderTest {
    @BeforeAll
    public static void reload(){
        try {
            RouteReader.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRouteReadersTest(){
        List<RouteReader> readers= RouteReader.getRouteReaders();
        Assertions.assertEquals(readers.size(),47);
    }

    @Test
    public void getRouteReaderTest(){
        RouteReader reader= RouteReader.getRouteReader("SPG");
        Assertions.assertNotNull(reader);

        Assertions.assertEquals(reader.getName(),"Sand Patch Grade");
        Assertions.assertEquals(reader.getNation(),"USA");
    }

    @Test
    public void getRouteTest(){
        Route route=RouteReader.getRouteReader("SPG").getRoute();

        Assertions.assertNotNull(route);
        Assertions.assertEquals(route.getName(),"Sand Patch Grade");
    }
    @Test
    public void getLocoTest(){
        List<Locomotive> locos=RouteReader.getRouteReader("SPG").getLocomotives();

        Assertions.assertNotNull(locos);
        Assertions.assertEquals(locos.size(),5);
    }
}
