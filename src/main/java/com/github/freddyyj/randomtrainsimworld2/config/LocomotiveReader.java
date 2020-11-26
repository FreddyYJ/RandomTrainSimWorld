package com.github.freddyyj.randomtrainsimworld2.config;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * reader of all locomotive json file
 * <p>
 *     This class reads all routes and locomotives from locomotives.json in jar.
 *
 *     One object has one route, and its locomotives.
 * </p>
 */
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

    /**
     * Reload locomotives.json
     */
    public static void reload(){
        JsonReader reader= Json.createReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("locomotives.json"));
        JsonObject object=reader.readObject();

        Set<String> set=object.keySet();
        for (int i=0;i< set.size();i++){
            ArrayList<String> locos=new ArrayList<>();
            String key=(set.toArray(new String[1]))[i];
            JsonObject route=object.getJsonObject(key);

            route.getJsonArray("locomotives").forEach(locomotive->{
                locos.add(locomotive.toString());
            });
            routes.add(new LocomotiveReader(key,route.getString("name"),locos,route.getString("nation")));
        }

        reader.close();

    }

    /**
     * Get {@link LocomotiveReader} list
     * @return LocomotiveReader list
     */
    public static List<LocomotiveReader> getLocomotiveReaders(){return routes;}

    /**
     * Get one {@link LocomotiveReader} object with route code
     * @param code route code
     * @return LocomotiveReader object
     */
    public static LocomotiveReader getLocomotiveReader(String code){
        for (int i=0;i<routes.size();i++){
            if (routes.get(i).getCode().equals(code)) return routes.get(i);
        }
        return null;
    }
    public String getCode(){return code;}
    public String getName(){return name;}
    public String getNation(){return nation;}

    /**
     * Get locomotives list from this route
     * @return locomotive list
     */
    public List<String> getLocomotives(){return locomotives;}
}
