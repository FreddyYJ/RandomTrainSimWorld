package com.github.freddyyj.randomtrainsimworld2.config;

import com.github.freddyyj.randomtrainsimworld2.Main;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class WeatherReader {
    private static ArrayList<String> weathers=new ArrayList<>();
    private WeatherReader(){}
    public static void reload(){
        JsonReader reader=Json.createReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("weathers.json"));
        JsonArray array=reader.readArray();

        for (int i=0;i< array.size();i++){
            weathers.add(array.getString(i));
        }

        reader.close();

    }
    public static List<String> getWeathers(){return weathers;}
}
