package com.github.freddyyj.randomtrainsimworld2.config;

import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Default configuration class
 * Don't create this object directly. Use {@link com.github.freddyyj.randomtrainsimworld2.Main} methods instead.
 * @author FreddyYJ_
 */
public class Config {
	private static final String FILE_NAME="/randomtrainsimworld2.json";
	private JSONObject object;
	private String documentFile=javax.swing.filechooser.FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
	private File saveFile;

	/**
	 * Constructor
	 */
	public Config() {
		saveFile=new File(documentFile+FILE_NAME);
		if (!saveFile.exists()) {
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JSONObject config=new JSONObject();
			config.put("DefaultSaveFilePath",null);
			object=config;
			
			try {
				config.writeJSONString(new FileWriter(saveFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			JSONParser parser=new JSONParser();
			object= (JSONObject) parser.parse(new FileReader(saveFile));
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add or update value of key.
	 * @param key target key
	 * @param value target value
	 */
	public void setConfig(String key,String value) {
		object.put(key, value);
	}

	/**
	 * Get value of key.
	 * <p>
	 *     If key doesn't exist, return null.
	 * </p>
	 * @param key target key
	 * @return value of key
	 */
	public String getConfig(String key) {
		if (object.get(key)==null || !object.containsKey(key))
			return null;
		return (String) object.get(key);
	}

	/**
	 * Save changes.
	 */
	public void save() {
		try {
			object.writeJSONString(new FileWriter(saveFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
