package com.github.freddyyj.randomtrainsimworld.config;

import com.github.freddyyj.randomtrainsimworld.Locomotive;
import com.github.freddyyj.randomtrainsimworld.Route;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
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
public class RouteReader {
    private static ArrayList<RouteReader> routes;
    private String code;
    private String name;
    private Route route;
    private List<Locomotive> locomotives;
    private String nation;
    private RouteReader(String code, String name, List<String> locos, String nation){
        this.code=code;
        this.name=name;
        this.nation=nation;
        this.route=new Route(name,code);
        this.locomotives=new ArrayList<>();
        for (String loco:locos){
            locomotives.add(new Locomotive(loco,route));
        }
    }

    /**
     * Reload locomotives.json.
     * <p>
     *     This method will create all objects new.
     *     You should reset all settings(e.g. {@link SaveLoco}).
     * </p>
     */
    public static void reload() throws IOException {
        routes=new ArrayList<>();
        JsonObject object= JsonParser.parseReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("locomotives.json"))).getAsJsonObject();

        Set<String> set=object.keySet();
        for (int i=0;i< set.size();i++){
            ArrayList<String> locos=new ArrayList<>();
            String key=(set.toArray(new String[1]))[i];
            JsonObject route= object.getAsJsonObject(key);

            route.get("locomotives").getAsJsonArray().forEach(locomotive-> {
                String loco=locomotive.toString();
                loco=loco.substring(1,loco.length()-1);
                locos.add(loco);
            });
            routes.add(new RouteReader(key,route.get("name").getAsString(),locos,route.get("nation").getAsString()));
        }
    }

    /**
     * Get {@link RouteReader} list
     * @return LocomotiveReader list
     */
    public static List<RouteReader> getRouteReaders(){return routes;}

    /**
     * Get one {@link RouteReader} object with route code
     * @param code route code
     * @return LocomotiveReader object
     */
    public static RouteReader getRouteReader(String code){
        for (int i=0;i<routes.size();i++){
            if (routes.get(i).getCode().equals(code)) return routes.get(i);
        }
        return null;
    }
    public String getCode(){return code;}
    public String getName(){return name;}
    public String getNation(){return nation;}
    public Route getRoute(){return route;}

    /**
     * Get locomotives list from this route
     * @return locomotive list
     */
    public List<Locomotive> getLocomotives(){return locomotives;}
}
