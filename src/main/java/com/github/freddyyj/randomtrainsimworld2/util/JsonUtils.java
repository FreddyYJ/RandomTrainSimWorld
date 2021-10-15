package com.github.freddyyj.randomtrainsimworld2.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * utilities related with Json
 * @author FreddyYJ_
 * @version 0.2.0
 */
public class JsonUtils {
    /**
     * write {@link JsonObject} to file.
     * @param object JsonObject that want to write
     * @param targetFile target file
     * @throws IOException If output error occurred
     */
    public static void write(JsonObject object, File targetFile) throws IOException {
        FileWriter writer=new FileWriter(targetFile, StandardCharsets.UTF_16BE);
        writer.write(object.toString());
        writer.close();
    }

    /**
     * write {@link JsonArray} to file.
     * @param object JsonArray that want to write
     * @param targetFile target file
     * @throws IOException If output error occurred
     */
    public static void write(JsonArray object, File targetFile) throws IOException {
        FileWriter writer=new FileWriter(targetFile,StandardCharsets.UTF_16BE);
        writer.write(object.toString());
        writer.close();
    }

}
