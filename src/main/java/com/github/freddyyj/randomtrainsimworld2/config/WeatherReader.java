package com.github.freddyyj.randomtrainsimworld2.config;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public static void reload() throws IOException, ParseException {
        JSONParser parser=new JSONParser();
        JSONArray weather= (JSONArray) parser.parse(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("weathers.json")));

        for (Object o : weather) {
            weathers.add((String) o);
        }
    }

    /**
     * Get list of all weathers
     * @return weather list
     */
    public static List<String> getWeathers(){return weathers;}
}
