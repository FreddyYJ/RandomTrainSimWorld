package com.github.freddyyj.randomtrainsimworld.config;

import com.github.freddyyj.randomtrainsimworld.Weather;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Reader class of weathers JSON file
 * <p>
 *     This class reads weathers.json in jar. This class has only static methods.
 * </p>
 * @author FreddyYJ_
 */
public class WeatherReader {
    private static ArrayList<Weather> weathers=new ArrayList<>();
    private WeatherReader(){}

    /**
     * Reload weathers.json
     */
    public static void reload() throws IOException {
        weathers=new ArrayList<>();
        JsonArray weather= JsonParser.parseReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("weathers.json"))).getAsJsonArray();

        for (int i=0;i<weather.size();i++) {
            String o=weather.get(i).getAsString();
            weathers.add(new Weather(o));
        }
    }

    /**
     * Get list of all weathers
     * @return weather list
     */
    public static List<Weather> getWeathers(){return weathers;}
}
