package com.github.freddyyj.randomtrainsimworld2.config;


import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LocomotiveReader {
    private static ArrayList<LocomotiveReader> routes=new ArrayList<>();
    private String code;
    private String name;
    private List<String> locomotives;
    private String nation;
    private LocomotiveReader(String code, String name, List<String> locos,String nation){
        this.code=code;
        this.name=name;
        this.nation=nation;
        locomotives=locos;
    }
    public static void reload(){
        JsonReader reader= Json.createReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("locomotives.json"));
        JsonObject object=reader.readObject();
        ArrayList<String> locos=new ArrayList<>();

        Set<String> set=object.keySet();
        for (int i=0;i< set.size();i++){
            locos.clear();
            String key=(set.toArray(new String[1]))[i];
            JsonObject route=object.getJsonObject(key);

            route.getJsonArray("locomotives").forEach(locomotive->{
                locos.add(locomotive.toString());
            });
            routes.add(new LocomotiveReader(key,route.getString("name"),locos,route.getString("nation")));
        }

        reader.close();

    }
    public static List<LocomotiveReader> getLocomotiveReaders(){return routes;}
    public static LocomotiveReader getLocomotiveReader(String code){
        for (int i=0;i<routes.size();i++){
            if (routes.get(i).getCode().equals(code)) return routes.get(i);
        }
        return null;
    }
    public String getCode(){return code;}
    public String getName(){return name;}
    public String getNation(){return nation;}
    public List<String> getLocomotives(){return locomotives;}
}
