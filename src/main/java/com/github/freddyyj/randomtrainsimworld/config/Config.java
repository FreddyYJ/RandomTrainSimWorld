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
	public static final String FILE_NAME="/randomtsw.json";
	private JsonObject object;
	private String documentFile;
}
