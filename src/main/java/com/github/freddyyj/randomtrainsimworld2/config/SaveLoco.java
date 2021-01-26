package com.github.freddyyj.randomtrainsimworld2.config;

import javax.json.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Class for managing savefile.
 * Don't create this object. Use {@link com.github.freddyyj.randomtrainsimworld2.Main#setSaveFilePath(String)} or {@link com.github.freddyyj.randomtrainsimworld2.Main#saveAs(String)} instead.
 */
public class SaveLoco {
	private JsonObject object;
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
		JsonObjectBuilder builder=Json.createObjectBuilder();
		
		builder.add("route",Json.createArrayBuilder());
		
		for (int i=0;i<routes.size();i++) {
			builder.add(routes.get(i),Json.createArrayBuilder());
		}
		builder.add("weather", Json.createArrayBuilder());
		
		object=builder.build();
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
				
				JsonObjectBuilder builder=Json.createObjectBuilder();
				
				builder.add("route",Json.createArrayBuilder());
				
				for (int i=0;i<routes.size();i++) {
					builder.add(routes.get(i),Json.createArrayBuilder());
				}
				builder.add("weather", Json.createArrayBuilder());
				
				object=builder.build();
				FileOutputStream writer=new FileOutputStream(saveFile);
				JsonWriter jsonWriter=Json.createWriter(writer);
				jsonWriter.writeObject(object);
				jsonWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			FileInputStream reader=new FileInputStream(saveFile);
			JsonReader jsonReader=Json.createReader(reader);
			object=jsonReader.readObject();
			jsonReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Check if save file is set.
	 * <p>
	 *     Savefile is only not set when before save first save file.
	 *     If you save savefile once, it will always be {@code true}.
	 * </p>
	 * @return true when set
	 */
	public boolean hasSavefile(){
		return saveFile != null;
	}

	/**
	 * Reload savefile.
	 */
	public void reload() {
		FileInputStream reader;
		try {
			reader = new FileInputStream(saveFile);
			JsonReader jsonReader=Json.createReader(reader);
			object=jsonReader.readObject();
			jsonReader.close();
			isChanged=false;
		} catch (FileNotFoundException e) {
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
		JsonArray routeArray= (JsonArray) object.get("route");
		ArrayList<String> routeStrings=new ArrayList<>();
		for (int i=0;i<routeArray.size();i++) {
			routeStrings.add(routeArray.getString(i));
		}
		return routeStrings;
		
	}
	/**
	 * Get locomotive list that unselected.
	 * @return unselected locomotive list
	 */
	public ArrayList<String> getLocomotive(String route){
		JsonArray locoArray= (JsonArray) object.get(route);
		if (locoArray==null) {
			JsonObjectBuilder builder=Json.createObjectBuilder(object);
			builder.add(route, Json.createArrayBuilder());
			object=builder.build();
			locoArray= (JsonArray) object.get(route);
		}
		ArrayList<String> locoStrings=new ArrayList<>();
		for (int i=0;i<locoArray.size();i++) {
			locoStrings.add(locoArray.getString(i));
		}
		return locoStrings;

	}
	/**
	 * Get weather list that unselected.
	 * @return unselected weather list
	 */
	public ArrayList<String> getWeather(){
		JsonArray weatherArray= (JsonArray) object.get("weather");
		ArrayList<String> weatherStrings=new ArrayList<>();
		for (int i=0;i<weatherArray.size();i++) {
			weatherStrings.add(weatherArray.getString(i));
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
		JsonArray routeArray= (JsonArray) object.get("route");
		JsonArrayBuilder builder=Json.createArrayBuilder(routeArray);
		if (find(routeArray, route)==-1) {
			builder.add(route);
			JsonObjectBuilder objectBuilder=Json.createObjectBuilder(object);
			objectBuilder.add("route", builder);
			object=objectBuilder.build();
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
		JsonArray locoArray=(JsonArray) object.get(route);
		JsonArrayBuilder builder=Json.createArrayBuilder(locoArray);
		if (find(locoArray, loco)==-1) {
			builder.add(loco);
			JsonObjectBuilder objectBuilder=Json.createObjectBuilder(object);
			objectBuilder.add(route, builder);
			object=objectBuilder.build();
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
		JsonArray weatherArray= (JsonArray) object.get("weather");
		JsonArrayBuilder builder=Json.createArrayBuilder(weatherArray);
		if (find(weatherArray, weather)==-1) {
			builder.add(weather);
			JsonObjectBuilder objectBuilder=Json.createObjectBuilder(object);
			objectBuilder.add("weather", builder);
			object=objectBuilder.build();
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
		JsonArray routeArray= (JsonArray) object.get("route");
		JsonArrayBuilder builder=Json.createArrayBuilder(routeArray);
		if (find(routeArray, route)!=-1) {
			builder.remove(find(routeArray, route));
			JsonObjectBuilder objectBuilder=Json.createObjectBuilder(object);
			objectBuilder.add("route", builder);
			object=objectBuilder.build();
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
		JsonArray locoArray=(JsonArray) object.get(route);
		JsonArrayBuilder builder=Json.createArrayBuilder(locoArray);
		if (find(locoArray, loco)!=-1) {
			builder.remove(find(locoArray, loco));
			JsonObjectBuilder objectBuilder=Json.createObjectBuilder(object);
			objectBuilder.add(route, builder);
			object=objectBuilder.build();
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
		JsonArray weatherArray= (JsonArray) object.get("weather");
		JsonArrayBuilder builder=Json.createArrayBuilder(weatherArray);
		if (find(weatherArray, weather)!=-1) {
			builder.remove(find(weatherArray, weather));
			JsonObjectBuilder objectBuilder=Json.createObjectBuilder(object);
			objectBuilder.add("weather", builder);
			object=objectBuilder.build();
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
			FileOutputStream writer = new FileOutputStream(file);
			JsonWriter jsonWriter=Json.createWriter(writer);
			jsonWriter.writeObject(object);
			jsonWriter.close();
			isChanged=false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	private int find(JsonArray array,String string) {
		for (int i=0;i<array.size();i++) {
			if (array.getString(i).equals(string))
				return i;
		}
		return -1;
	}

	public boolean isChanged(){
		return isChanged;
	}
}
