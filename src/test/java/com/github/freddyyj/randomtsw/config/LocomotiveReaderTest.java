package com.github.freddyyj.randomtsw.config;

import com.github.freddyyj.randomtrainsimworld2.config.LocomotiveReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class LocomotiveReaderTest {
    private static List<LocomotiveReader> locos;
    @BeforeAll
    public static void reload(){
        try {
            LocomotiveReader.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }

        locos=LocomotiveReader.getLocomotiveReaders();
    }

    @Test
    public void getReaderTest(){
        LocomotiveReader reader=LocomotiveReader.getLocomotiveReader("SPG");
        Assertions.assertNotNull(reader);

        Assertions.assertEquals(reader.getName(),"Sand Patch Grade");
        Assertions.assertEquals(reader.getNation(),"USA");
    }
}
