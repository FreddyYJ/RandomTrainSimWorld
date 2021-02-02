package com.github.freddyyj.randomtrainsimworld2.config;

import com.github.freddyyj.randomtrainsimworld2.exception.PermissionDeniedException;
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
	public static final String FILE_NAME="/randomtrainsimworld2.json";
	private JsonObject object;
	private String documentFile=javax.swing.filechooser.FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
	private File saveFile;

	/**
	 * Constructor
	 */
	public Config() throws IOException {
		saveFile=new File(documentFile+FILE_NAME);
		if (!saveFile.exists()) {
			try {
				saveFile.createNewFile();
			}catch (SecurityException e){
				throw new PermissionDeniedException(e.getMessage(),saveFile.getName());
			}

			JsonObject config=new JsonObject();
			config.add("DefaultSaveFilePath",JsonNull.INSTANCE);
			object=config;
			
			JsonUtils.write(config,saveFile);
		}
		try {
			object= JsonParser.parseReader(new FileReader(saveFile)).getAsJsonObject();
		} catch (FileNotFoundException e) {
			throw new com.github.freddyyj.randomtrainsimworld2.exception.FileNotFoundException(e.getMessage(),saveFile.getName());
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
		if (object.get(key) instanceof JsonNull || !object.has(key))
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
