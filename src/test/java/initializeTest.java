import com.github.freddyyj.randomtrainsimworld2.Main;
import org.junit.Assert;
import org.junit.Test;

public class initializeTest {
    @Test
    public void mainInitTest(){
        Main core=Main.getInstance();

        Assert.assertEquals(core.getLocomotive("Long Island Railroad").get(0).getName(),"M7 LIRR");
    }
}
