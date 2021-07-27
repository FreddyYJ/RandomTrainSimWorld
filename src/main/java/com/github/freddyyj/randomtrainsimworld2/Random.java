package com.github.freddyyj.randomtrainsimworld2;

import com.github.freddyyj.randomtrainsimworld2.exception.NoElementSelectedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Random picker class.
 * <p>
 *     Extend this class and call {@link com.github.freddyyj.randomtrainsimworld2.gui.MainController#setRandom(Random)} to change your custom random class.
 *     This class should be singleton.
 * </p>
 * @author FreddyYJ_
 */
public class Random {
    private static Random random=null;
    private java.util.Random randomObject;

    /**
     * Constructor
     */
    protected Random(){
        randomObject=new java.util.Random();
    }

    /**
     * Get instance of this class.
     * @return Random
     */
    public static Random getInstance(){
        if (random==null) random=new Random();
        return random;
    }

    /**
     * Pick random route.
     * <p>
     *     Each route has same probability.
     * </p>
     * @param routes list of routes
     * @return picked route
     */
    public Route randomRoute(List<Route> routes){
        int i,temp=0;
        for (i=0;i<routes.size();i++){
            if (!routes.get(i).isSelected) temp++;
        }
        if (temp==i) throw new NoElementSelectedException("No Route Selected! Select one or more route.");

        int size=routes.size();
        int randomValue=randomObject.nextInt(size);
        while(!routes.get(randomValue).isSelected)
            randomValue=randomObject.nextInt(size);

        return routes.get(randomValue);
    }

    /**
     * Pick random locomotive in a list.
     * <p>
     *     Each locomotive has same probability.
     * </p>
     * @param locomotives list of locomotives
     * @return picked locomotive
     */
    public Locomotive randomLocomotive(List<Locomotive> locomotives){
        int i,temp=0;
        for (i=0;i<locomotives.size();i++){
            if (!locomotives.get(i).isSelected) temp++;
        }
        if (temp==i) throw new NoElementSelectedException("No Locomotive Selected! Select one or more locomotive.");

        int size=locomotives.size();
        int randomValue=randomObject.nextInt(size);
        while(!locomotives.get(randomValue).isSelected)
            randomValue=randomObject.nextInt(size);

        return locomotives.get(randomValue);
    }

    /**
     * Pick random locomotive in multiple list.
     * <p>
     *     Each locomotive has same probability.
     * </p>
     * @param locomotives list of list of locomotives
     * @return picked locomotive
     */
    public Locomotive randomLocomotiveInAll(List<List<Locomotive>> locomotives){
        ArrayList<Locomotive> list=new ArrayList<>();
        for (int i=0;i<locomotives.size();i++){
            list.addAll(locomotives.get(i));
        }

        int i,temp=0;
        for (i=0;i<list.size();i++){
            if (!list.get(i).isSelected) temp++;
        }
        if (temp==i) throw new NoElementSelectedException("No Locomotive Selected! Select one or more locomotive.");

        temp=0;
        for (i=0;i<locomotives.size();i++){
            if (!locomotives.get(i).get(0).getRoute().isSelected) temp++;
        }
        if (temp==i) throw new NoElementSelectedException("No Route Selected! Select one or more route.");


        int size=list.size();
        int randomValue=randomObject.nextInt(size);
        while(!list.get(randomValue).isSelected || !list.get(randomValue).getRoute().isSelected){
            randomValue=randomObject.nextInt(size);
        }

        return list.get(randomValue);
    }

    /**
     * Pick random weather.
     * <p>
     *     Each weather has same probability.
     * </p>
     * @param weathers list of weathers
     * @return picked weather
     */
    public Weather randomWeather(List<Weather> weathers){
        int i,temp=0;
        for (i=0;i<weathers.size();i++){
            if (!weathers.get(i).isSelected) temp++;
        }
        if (temp==i) throw new NoElementSelectedException("No Weather Selected! Select one or more weather.");

        int size=weathers.size();
        int randomValue=randomObject.nextInt(size);
        while(!weathers.get(randomValue).isSelected)
            randomValue=randomObject.nextInt(size);

        return weathers.get(randomValue);
    }
}
