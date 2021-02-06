package com.github.freddyyj.randomtrainsimworld2.config;

import com.github.freddyyj.randomtrainsimworld2.exception.PermissionDeniedException;
import com.github.freddyyj.randomtrainsimworld2.util.JsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;

/**
 * Class for managing savefile.
 * Don't create this object. Use {@link com.github.freddyyj.randomtrainsimworld2.Main#setSaveFilePath(String)} or {@link com.github.freddyyj.randomtrainsimworld2.Main#saveAs(String)} instead.
 */
public class SaveLoco {
	private JsonObject object;
	private File saveFile=null;
	private boolean isChanged=false;

	/**
	 * Constructor with route list
	 * <p>
	 *     Route list most has all routes.
	 * </p>
	 * @param routes list of all routes
	 */
	public SaveLoco(ArrayList<String> routes) {
		object=new JsonObject();
		
		for (int i=0;i<routes.size();i++) {
			object.add(routes.get(i),new JsonArray());
		}
		object.add("weather", new JsonArray());
		
		isChanged=true;
	}

	/**
	 * Constructor with route list and savefile path
	 * <p>
	 *     Route list most has all routes.
	 * </p>
	 * @param routes list of all routes
	 * @param defaultPath savefile path
	 * @throws PermissionDeniedException If save file cannot accessible
	 * @throws com.github.freddyyj.randomtrainsimworld2.exception.FileNotFoundException If save file not exist
	 * @throws IOException If IO errors occurred
	 */
	public SaveLoco(ArrayList<String> routes,String defaultPath) throws IOException {
		saveFile=new File(defaultPath);
		if (!saveFile.exists()) {
			try {
				saveFile.createNewFile();
			}catch (SecurityException e){
				throw new PermissionDeniedException(e.getMessage(),saveFile.getName());
			}

			object=new JsonObject();
			object.add("route",new JsonArray());

			for (String route : routes) {
				object.add(route, new JsonArray());
			}
			object.add("weather", new JsonArray());

			save(saveFile);
		}

		try {
			object = JsonParser.parseReader(new FileReader(saveFile)).getAsJsonObject();
		}catch (FileNotFoundException e){
			throw new com.github.freddyyj.randomtrainsimworld2.exception.FileNotFoundException(e.getMessage(),saveFile.getName());
		}

	}
	public boolean hasSavefile(){
		return saveFile != null;
	}

	/**
	 * Reload savefile.
	 */
	public void reload() throws com.github.freddyyj.randomtrainsimworld2.exception.FileNotFoundException {
		try {
			object= JsonParser.parseReader(new FileReader(saveFile)).getAsJsonObject();
			isChanged=false;
		} catch (FileNotFoundException e) {
			throw new com.github.freddyyj.randomtrainsimworld2.exception.FileNotFoundException(e.getMessage(),saveFile.getName());
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
		JsonArray routeArray= object.getAsJsonArray("route");
		ArrayList<String> routeStrings=new ArrayList<>();
		for (int i=0;i<routeArray.size();i++) {
			routeStrings.add(routeArray.get(i).getAsString());
		}
		return routeStrings;
		
	}
	/**
	 * Get locomotive list that unselected.
	 * @return unselected locomotive list
	 */
	public ArrayList<String> getLocomotive(String route){
		JsonArray locoArray= object.getAsJsonArray(route);
		if (locoArray==null) {
			object.add(route, new JsonArray());
			locoArray= object.getAsJsonArray(route);
		}

		ArrayList<String> locoStrings=new ArrayList<>();
		for (int i=0;i<locoArray.size();i++) {
			locoStrings.add(locoArray.get(i).getAsString());
		}
		return locoStrings;

	}
	/**
	 * Get weather list that unselected.
	 * @return unselected weather list
	 */
	public ArrayList<String> getWeather(){
		JsonArray weatherArray= object.getAsJsonArray("weather");
		ArrayList<String> weatherStrings=new ArrayList<>();
		for (int i=0;i<weatherArray.size();i++) {
			weatherStrings.add(weatherArray.get(i).getAsString());
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
		JsonArray routeArray=object.getAsJsonArray("route");
		if (find(routeArray, route)==-1) {
			routeArray.add(route);
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
		JsonArray locoArray=object.getAsJsonArray(route);
		if (find(locoArray, loco)==-1) {
			object.addProperty(route, loco);
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
		JsonArray weatherArray= object.getAsJsonArray("weather");
		if (find(weatherArray, weather)==-1) {
			object.addProperty("weather", weather);
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
		JsonArray routeArray= object.getAsJsonArray("route");
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
		JsonArray locoArray=object.getAsJsonArray(route);
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
		JsonArray weatherArray= object.getAsJsonArray("weather");
		if (find(weatherArray, weather)!=-1) {
			weatherArray.remove(find(weatherArray, weather));
			isChanged=true;
		}
	}

	/**
	 * Save savefile.
	 */
	public void save() throws IOException {
		this.save(saveFile);
	}

	/**
	 * Save savefile at specific {@link File}.
	 * @param file savefile file
	 */
	public void save(File file) throws IOException {
			JsonUtils.write(object,file);
			isChanged=false;
	}
	private int find(JsonArray array,String string) {
		for (int i=0;i<array.size();i++) {
			if (array.get(i).getAsString().equals(string))
				return i;
		}
		return -1;
	}

	public boolean isChanged(){
		return isChanged;
	}
}
