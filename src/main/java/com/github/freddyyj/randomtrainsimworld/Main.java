package com.github.freddyyj.randomtrainsimworld;

import com.github.freddyyj.randomtrainsimworld.config.Config;
import com.github.freddyyj.randomtrainsimworld.exception.FileNotFoundException;
import com.google.common.collect.ImmutableList;
import javafx.application.Application;

import java.io.IOException;
import java.util.*;

/**
 * main class for control logics
 * This class should be Singleton. Use {@link Main#getInstance()} to get this object.
 * @author FreddyYJ_
 */
public class Main {
	public final ImmutableList<Route> routes;
	public final ImmutableList<Locomotive> locomotives;
	private Map<Route,List<String>> selectedRoutes;
	private Config config;
	private static Main core=null;

	public static void main(String[] args) {
		Main main = Main.getInstance();
		Application.launch(com.github.freddyyj.randomtrainsimworld.gui.Main.class);
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
	 * Save and close save file.
	 */
	public void close() throws IOException {
		if (unselectedLocos.hasSavefile()) unselectedLocos.save();
	}

	/**
	 * Save savefile at specific path.
	 * @param path file path
	 */
	public void saveAs(String path) throws IOException {
		setSaveFilePath(path);
		unselectedLocos.save();
	}

	/**
	 * Reload savefile from specific path.
	 * @param path file path
	 */
	public void reloadSaveFile(String path) throws FileNotFoundException {
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
	public void saveConfig() throws IOException {
		config.save();
	}
}
