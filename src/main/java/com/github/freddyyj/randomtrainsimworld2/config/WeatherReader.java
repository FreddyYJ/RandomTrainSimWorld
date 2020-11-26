package com.github.freddyyj.randomtrainsimworld2.config;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Reader class of weathers JSON file
 * <p>
 *     This class reads weathers.json in jar. This class has only static class.
 * </p>
 * @author FreddyYJ_
 */
public class WeatherReader {
    private static ArrayList<String> weathers=new ArrayList<>();
    private WeatherReader(){}

    /**
     * Reload weathers.json
     */
    public static void reload(){
        JsonReader reader=Json.createReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("weathers.json"));
        JsonArray array=reader.readArray();

        for (int i=0;i< array.size();i++){
            weathers.add(array.getString(i));
        }

        reader.close();

    }

    /**
     * Get list of all weathers
     * @return weather list
     */
    public static List<String> getWeathers(){return weathers;}
}
