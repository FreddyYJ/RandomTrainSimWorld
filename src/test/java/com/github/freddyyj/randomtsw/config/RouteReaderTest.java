package com.github.freddyyj.randomtsw.config;

import com.github.freddyyj.randomtrainsimworld2.config.RouteReader;
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
        Assertions.assertEquals(readers.size(),27);
    }

    @Test
    public void getRouteReaderTest(){
        RouteReader reader= RouteReader.getRouteReader("SPG");
        Assertions.assertNotNull(reader);

        Assertions.assertEquals(reader.getName(),"Sand Patch Grade");
        Assertions.assertEquals(reader.getNation(),"USA");
    }
}
