import com.github.freddyyj.randomtrainsimworld2.config.LocomotiveReader;
import com.github.freddyyj.randomtrainsimworld2.config.WeatherReader;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class IOStreamTest {
    @Test
    public void readLocomotiveTest(){
        try {
            LocomotiveReader.reload();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(LocomotiveReader.getLocomotiveReader("NTP").getName(),"Northern-Trans Pennine");
    }
    @Test
    public void readWeatherTest(){
        try {
            WeatherReader.reload();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        List<String> list=WeatherReader.getWeathers();

        Assert.assertEquals(16, list.size());
        Assert.assertEquals("Summer Clear", list.get(3));
    }
}
