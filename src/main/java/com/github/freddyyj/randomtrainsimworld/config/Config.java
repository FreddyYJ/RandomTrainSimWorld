package com.github.freddyyj.randomtrainsimworld.config;

import com.github.freddyyj.randomtrainsimworld.exception.PermissionDeniedException;
import com.github.freddyyj.randomtrainsimworld.util.JsonUtils;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Default configuration class
 * Don't create this object directly. Use {@link com.github.freddyyj.randomtrainsimworld.Main} methods instead.
 * @author FreddyYJ_
 */
public class Config {
	public static final String FILE_NAME="/randomtrainsimworld.json";
	private JsonObject object;
	private String documentFile=javax.swing.filechooser.FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
	private File saveFile;

	/**
	 * Constructor
	 * @throws com.github.freddyyj.randomtrainsimworld.exception.FileNotFoundException Throws when config file({@code FILE_NAME}) missing
	 * @throws IOException If I/O Exception occurred
	 */
	public Config() throws IOException {
		saveFile=new File(documentFile+FILE_NAME);
		if (!saveFile.exists()) {
			createConfig();
		}
		try {
			object= JsonParser.parseReader(new FileReader(saveFile,StandardCharsets.UTF_16)).getAsJsonObject();
		} catch (FileNotFoundException e) {
			throw new com.github.freddyyj.randomtrainsimworld.exception.FileNotFoundException(e.getMessage(),saveFile.getName());
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
	 * @throws IOException If I/O exception occurred
	 */
	public void save() throws IOException {
			JsonUtils.write(object,saveFile);
	}
	private void createConfig() throws IOException {
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
}
