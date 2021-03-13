package com.github.freddyyj.randomtsw;

import com.github.freddyyj.randomtrainsimworld2.config.LocomotiveReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ReaderTest {
    @Test
    public void locomotiveReadTest(){
        try {
            LocomotiveReader.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocomotiveReader reader=LocomotiveReader.getLocomotiveReader("SPG");
        assert reader!=null;
        assert reader.getCode().equals("SPG");
        assert reader.getName().equals("Sand Patch Grade");

        assert reader.getLocomotives().get(0).equals("GP38-2 CSX");
    }
}
