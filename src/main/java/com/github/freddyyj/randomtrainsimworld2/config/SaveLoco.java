package com.github.freddyyj.randomtrainsimworld2.config;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

/**
 * Class for managing savefile.
 * Don't create this object. Use {@link com.github.freddyyj.randomtrainsimworld2.Main#setSaveFilePath(String)} or {@link com.github.freddyyj.randomtrainsimworld2.Main#saveAs(String)} instead.
 */
public class SaveLoco {
	private JSONObject object;
	private File saveFile;
	private boolean isChanged=false;

	/**
	 * Constructor with route list
	 * <p>
	 *     Route list most has all routes.
	 * </p>
	 * @param routes list of all routes
	 */
	public SaveLoco(ArrayList<String> routes) {
		object=new JSONObject();
		object.put("route",new JSONArray());
		
		for (int i=0;i<routes.size();i++) {
			object.put(routes.get(i),new JSONArray());
		}
		object.put("weather", new JSONArray());
		
		isChanged=true;
	}

	/**
	 * Constructor with route list and savefile path
	 * <p>
	 *     Route list most has all routes.
	 * </p>
	 * @param routes list of all routes
	 * @param defaultPath savefile path
	 */
	public SaveLoco(ArrayList<String> routes,String defaultPath) {
		saveFile=new File(defaultPath);
		if (!saveFile.exists()) {
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				object=new JSONObject();
				object.put("route",new JSONArray());

				for (String route : routes) {
					object.put(route, new JSONArray());
				}
				object.put("weather", new JSONArray());

				object.writeJSONString(new FileWriter(saveFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			JSONParser parser=new JSONParser();
			object= (JSONObject) parser.parse(new FileReader(saveFile));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

	}
	public boolean hasSavefile(){
		return saveFile != null;
	}

	/**
	 * Reload savefile.
	 */
	public void reload() {
		try {
			JSONParser parser=new JSONParser();
			object= (JSONObject) parser.parse(new FileReader(saveFile));
			isChanged=false;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set savefile path.
	 * @param path file path
	 */
	public void setFilePath(String path) {
		saveFile=new File(path);
	}

	/**
	 * Get route list that unselected.
	 * @return unselected route list
	 */
	public ArrayList<String> getRoute(){
		JSONArray routeArray= (JSONArray) object.get("route");
		ArrayList<String> routeStrings=new ArrayList<>();
		for (Object o : routeArray) {
			routeStrings.add((String) o);
		}
		return routeStrings;
		
	}
	/**
	 * Get locomotive list that unselected.
	 * @return unselected locomotive list
	 */
	public ArrayList<String> getLocomotive(String route){
		JSONArray locoArray= (JSONArray) object.get(route);
		if (locoArray==null) {
			object.put(route, new JSONArray());
			locoArray= (JSONArray) object.get(route);
		}

		ArrayList<String> locoStrings=new ArrayList<>();
		for (int i=0;i<locoArray.size();i++) {
			locoStrings.add((String) locoArray.get(i));
		}
		return locoStrings;

	}
	/**
	 * Get weather list that unselected.
	 * @return unselected weather list
	 */
	public ArrayList<String> getWeather(){
		JSONArray weatherArray= (JSONArray) object.get("weather");
		ArrayList<String> weatherStrings=new ArrayList<>();
		for (int i=0;i<weatherArray.size();i++) {
			weatherStrings.add((String) weatherArray.get(i));
		}
		return weatherStrings;

	}

	/**
	 * Add route to savefile.
	 * <p>
	 *     This route must be unselected.
	 * </p>
	 * @param route unselected route name
	 */
	public void addRoute(String route) {
		JSONArray routeArray= (JSONArray) object.get("route");
		if (find(routeArray, route)==-1) {
			object.put("route", route);
			isChanged=true;
		}
	}

	/**
	 * Add locomotive to route.
	 * <p>
	 *     Locomotive must be unselected.
	 * </p>
	 * @param route route of locomotive
	 * @param loco locomotive name
	 */
	public void addLocomotive(String route,String loco) {
		JSONArray locoArray=(JSONArray) object.get(route);
		if (find(locoArray, loco)==-1) {
			object.put(route, loco);
			isChanged=true;
		}
	}

	/**
	 * Add weather to savefile.
	 * <p>
	 *     Weather must be unselected.
	 * </p>
	 * @param weather weather name
	 */
	public void addWeather(String weather) {
		JSONArray weatherArray= (JSONArray) object.get("weather");
		if (find(weatherArray, weather)==-1) {
			object.put("weather", weather);
			isChanged=true;
		}

	}

	/**
	 * Remove route from savefile.
	 * <p>
	 *     Route must be selected.
	 * </p>
	 * @param route route name
	 */
	public void removeRoute(String route) {
		JSONArray routeArray= (JSONArray) object.get("route");
		if (find(routeArray, route)!=-1) {
			routeArray.remove(find(routeArray, route));
			isChanged=true;
		}
	}

	/**
	 * Remove locomotive from route.
	 * <p>
	 *     Locomotive must be selected.
	 * </p>
	 * @param route route of locomotive
	 * @param loco locomotive name
	 */
	public void removeLocomotive(String route,String loco) {
		JSONArray locoArray=(JSONArray) object.get(route);
		if (find(locoArray, loco)!=-1) {
			locoArray.remove(find(locoArray, loco));
			isChanged=true;
		}
	}

	/**
	 * Remove weather from savefile.
	 * <p>
	 *     Weather must be selected.
	 * </p>
	 * @param weather weather name
	 */
	public void removeWeather(String weather) {
		JSONArray weatherArray= (JSONArray) object.get("weather");
		if (find(weatherArray, weather)!=-1) {
			weatherArray.remove(find(weatherArray, weather));
			isChanged=true;
		}
	}

	/**
	 * Save savefile.
	 */
	public void save() {
		this.save(saveFile);
	}

	/**
	 * Save savefile at specific {@link File}.
	 * @param file savefile file
	 */
	public void save(File file) {
		try {
			object.writeJSONString(new FileWriter(file));
			isChanged=false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private int find(JSONArray array,String string) {
		for (int i=0;i<array.size();i++) {
			if (array.get(i).equals(string))
				return i;
		}
		return -1;
	}

	public boolean isChanged(){
		return isChanged;
	}
}
