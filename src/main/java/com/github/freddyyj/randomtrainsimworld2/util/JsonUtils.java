package com.github.freddyyj.randomtrainsimworld2.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtils {
    public static void write(JsonObject object, File targetFile) throws IOException {
        FileWriter writer=new FileWriter(targetFile);
        writer.write(object.toString());
        writer.close();
    }
    public static void write(JsonArray object, File targetFile) throws IOException {
        FileWriter writer=new FileWriter(targetFile);
        writer.write(object.toString());
        writer.close();
    }

}
