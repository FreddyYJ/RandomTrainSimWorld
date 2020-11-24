import com.github.freddyyj.randomtrainsimworld2.config.WeatherReader;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class IOStreamTest {
    @Test
    public void readLocomotiveTest(){

    }
    @Test
    public void readWeatherTest(){
        WeatherReader.reload();
        List<String> list=WeatherReader.getWeathers();

        Assert.assertEquals(16, list.size());
        Assert.assertEquals("Summer Clear", list.get(3));
    }
}
