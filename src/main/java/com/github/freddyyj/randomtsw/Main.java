package com.github.freddyyj.randomtsw;

import java.util.*;

import com.github.freddyyj.randomtsw.config.Config;
import com.github.freddyyj.randomtsw.config.SaveLoco;

import javafx.application.Application;

/**
 * main class for control logics
 * This class should be Singleton. Use {@link Main#getInstance()} to get this object.
 * @author FreddyYJ_
 */
public class Main {
	private ArrayList<Route> routes;

	/**
	 * Get locomotive list.
	 * @return locomotive list
	 */
	public HashMap<Route, ArrayList<Locomotive>> getLocos() {
		return locos;
	}

	private HashMap<Route,ArrayList<Locomotive>> locos;
	private ArrayList<Weather> weathers;
	private SaveLoco unselectedLocos;
	private Config config;
	private static Main core=null;
	public static void main(String[] args) {
		Application.launch(com.github.freddyyj.randomtsw.gui.Main.class);
		
	}

	/**
	 * Get instance.
	 * @return instance of this class
	 */
	public static Main getInstance() {
		if (core==null) core=new Main();
		return core;
	}

	/**
	 * default constructor
	 */
	protected Main()
	{
		routes=new ArrayList<>();
		locos=new HashMap<>();
		weathers=new ArrayList<>();

		config=new Config();
		if (config.getConfig("DefaultSaveFilePath")==null)
			unselectedLocos=new SaveLoco(com.github.freddyyj.randomtsw.gui.Main.getController().getRouteList());
		else {
			unselectedLocos=new SaveLoco(com.github.freddyyj.randomtsw.gui.Main.getController().getRouteList(), config.getConfig("DefaultSaveFilePath"));
		}

		for (int i = 0; i< com.github.freddyyj.randomtsw.gui.Main.getController().getRouteList().size(); i++){
			Route route=new Route(com.github.freddyyj.randomtsw.gui.Main.getController().getRouteList().get(i));
			ArrayList<Locomotive> locoList=new ArrayList<>();
			for (int j = 0; j< com.github.freddyyj.randomtsw.gui.Main.getController().getLocoList().get(i).size(); j++){
				Locomotive locomotive=new Locomotive(com.github.freddyyj.randomtsw.gui.Main.getController().getLocoList().get(i).get(j),route);
				locoList.add(locomotive);
			}
			locos.put(route,locoList);
			routes.add(route);
		}

		for (int i = 0; i< com.github.freddyyj.randomtsw.gui.Main.getController().getWeather().size(); i++){
			Weather weather=new Weather(com.github.freddyyj.randomtsw.gui.Main.getController().getWeather().get(i),true);
			weathers.add(weather);
		}

		reload();
		com.github.freddyyj.randomtsw.gui.Main.getController().reload(unselectedLocos);
	}

	/**
	 * Get {@link SaveLoco} that has unselected routes and locos list.
	 * @return {@link SaveLoco} object
	 */
	public SaveLoco getUnselectedLocos() {
		return unselectedLocos;
	}

	/**
	 * Reload unselected routes, locos and weathers list.
	 */
	public void reload(){
		for (int i=0;i<routes.size();i++){
			for (int j=0;j<unselectedLocos.getRoute().size();j++){
				if (routes.get(i).getName().equals(unselectedLocos.getRoute().get(j))) {
					routes.get(i).isSelected=false;
					break;
				}
			}
			ArrayList<String> locos=unselectedLocos.getLocomotive(routes.get(i).getName());
			for (int k=0;k<this.locos.get(routes.get(i)).size();k++){
				for (int l=0;l<locos.size();l++){
					if (this.locos.get(routes.get(i)).get(k).getName().equals(locos.get(l))){
						this.locos.get(routes.get(i)).get(k).isSelected=false;
						break;
					}
				}
			}
		}

		for (int i=0;i<weathers.size();i++){
			for (int j=0;j<unselectedLocos.getWeather().size();j++){
				if (weathers.get(i).getName().equals(unselectedLocos.getWeather().get(j))){
					weathers.get(i).isSelected=false;
					break;
				}
			}
		}
	}

	/**
	 * Get route list.
	 * @return routes list
	 */
	public ArrayList<Route> getRoutes(){return routes;}

	/**
	 * Get weather list.
	 * @return weather list
	 */
	public ArrayList<Weather> getWeathers(){return weathers;}

	/**
	 * Get {@link Route} object with specific name.
	 * <p>
	 *     If {@link Route} object with name was not found, returns null.
	 * </p>
	 * @param name route name
	 * @return {@link Route} object
	 */
	public Route getRoute(String name) {
		for (int i=0;i<routes.size();i++) {
			if (routes.get(i).getName().equals(name)) {
				return routes.get(i);
			}
		}
		return null;
	}

	/**
	 * Get list of {@link Locomotive} objects with specific route name.
	 * <p>
	 *     If routeName was not found, returns null.
	 * </p>
	 * @param routeName route name
	 * @return list of {@link Locomotive} object
	 */
	public ArrayList<Locomotive> getLocomotive(String routeName) {
		Route route=getRoute(routeName);
		if (locos.get(route).size()>=1)
			return locos.get(route);
		else return null;
	}

	/**
	 * Get {@link Locomotive} object with specific route name and locomotive name.
	 * <p>
	 *     If routeName or locoName was not found, returns null.
	 * </p>
	 * @param routeName route name
	 * @param locoName locomotive name
	 * @return {@link Locomotive} object
	 */
	public Locomotive getLocomotive(String routeName,String locoName){
		ArrayList<Locomotive> locoList=getLocomotive(routeName);
		for (int i=0;i<locoList.size();i++){
			if (locoList.get(i).getName().equals(locoName)) return locoList.get(i);
		}
		return null;
	}

	/**
	 * Get {@link Weather} object with specific weather name.
	 * <p>
	 *     If not found, return null.
	 * </p>
	 * @param name weather name
	 * @return {@link Weather} object
	 */
	public Weather getWeather(String name) {
		for (int i=0;i<weathers.size();i++) {
			if (weathers.get(i).getName().equals(name)) {
				return weathers.get(i);
			}
		}
		return null;
	}

	/**
	 * Add or remove {@link Route} from routes list.
	 * @param isSelected add when true, remove when false
	 * @param route {@link Route} that want to add or remove
	 */
	public void selectRoute(boolean isSelected,Route route) {
		if (!isSelected) {
			unselectedLocos.addRoute(route.getName());
			route.isSelected=false;
		}
		else {
			unselectedLocos.removeRoute(route.getName());
			route.isSelected=true;
		}
	}
	/**
	 * Add or remove {@link Locomotive} from locomotive list.
	 * @param isSelected add when true, remove when false
	 * @param loco {@link Locomotive} that want to add or remove
	 * @param route {@link Route} of loco
	 */
	public void selectLocomotive(boolean isSelected,Locomotive loco,Route route) {
		ArrayList<Locomotive> locoList=getLocomotive(route.getName());
		if (!isSelected) {
			unselectedLocos.addLocomotive(route.getName(),loco.getName());
			loco.isSelected=false;
		}
		else {
			unselectedLocos.removeLocomotive(route.getName(),loco.getName());
			loco.isSelected=true;
		}

	}
	/**
	 * Add or remove {@link Weather} from weather list.
	 * @param isSelected add when true, remove when false
	 * @param weather {@link Weather} that want to add or remove
	 */
	public void selectWeather(boolean isSelected,Weather weather) {
		if (!isSelected) {
			unselectedLocos.addWeather(weather.getName());
			weather.isSelected=false;
		}
		else {
			unselectedLocos.removeWeather(weather.getName());
			weather.isSelected=true;
		}

	}

	/**
	 * Save and close save file.
	 */
	public void close() {
		unselectedLocos.save();
	}

	/**
	 * Save savefile at specific path.
	 * @param path file path
	 */
	public void saveAs(String path) {
		setSaveFilePath(path);
		unselectedLocos.save();
	}

	/**
	 * Reload savefile from specific path.
	 * @param path file path
	 */
	public void reloadSaveFile(String path) {
		setSaveFilePath(path);
		unselectedLocos.reload();
	}

	/**
	 * Set savefile path to specific path.
	 * @param path file path
	 */
	public void setSaveFilePath(String path) {
		config.setConfig("DefaultSaveFilePath", path);
		unselectedLocos.setFilePath(path);
	}

	/**
	 * Get savefile path.
	 * @return file path
	 */
	public String getSaveFilePath() {
		String path;
		try {
			 path=config.getConfig("DefaultSaveFilePath");
		}catch (NullPointerException e) {
			path=null;
		}
		return path;
	}

	/**
	 * Get routes, locos or weathers selection are changed.
	 * @return true if selection changed
	 */
	public boolean isSaveChanged(){return unselectedLocos.isChanged();}

	/**
	 * Save configuration.
	 */
	public void saveConfig() {
		config.save();
	}
}
