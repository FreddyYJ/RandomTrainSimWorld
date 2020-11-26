import com.github.freddyyj.randomtrainsimworld2.config.Config;
import org.junit.Assert;
import org.junit.Test;

public class SaveTest {
    @Test
    public void configTest(){
        Config config=new Config();

        Assert.assertEquals(null,config.getConfig("DefaultSaveFilePath"));
        config.setConfig("DefaultSaveFilePath","journey.json");
        config.save();
        Assert.assertEquals("journey.json",config.getConfig("DefaultSaveFilePath"));
    }
}
