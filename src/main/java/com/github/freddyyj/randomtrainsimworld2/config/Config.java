package com.github.freddyyj.randomtrainsimworld2.config;

import com.github.freddyyj.randomtrainsimworld2.util.JsonUtils;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

/**
 * Default configuration class
 * Don't create this object directly. Use {@link com.github.freddyyj.randomtrainsimworld2.Main} methods instead.
 * @author FreddyYJ_
 */
public class Config {
	private static final String FILE_NAME="/randomtrainsimworld2.json";
	private JsonObject object;
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
			JsonObject config=new JsonObject();
			config.add("DefaultSaveFilePath",JsonNull.INSTANCE);
			object=config;
			
			try {
				JsonUtils.write(config,saveFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			object= JsonParser.parseReader(new FileReader(saveFile)).getAsJsonObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add or update value of key.
	 * @param key target key
	 * @param value target value
	 */
	public void setConfig(String key,String value) {
		object.addProperty(key, value);
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
		if (object.get(key)==null || !object.has(key))
			return null;
		return object.get(key).getAsString();
	}

	/**
	 * Save changes.
	 */
	public void save() throws IOException {
			JsonUtils.write(object,saveFile);
	}
}
