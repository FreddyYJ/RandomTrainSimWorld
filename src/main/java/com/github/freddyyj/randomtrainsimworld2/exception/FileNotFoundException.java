package com.github.freddyyj.randomtrainsimworld2.exception;

public class FileNotFoundException extends java.io.FileNotFoundException {
    private String filePath;

    public FileNotFoundException(String file) {
        super();
        filePath=file;
    }

    public FileNotFoundException(String message,String file) {
        super(message);
        filePath=file;
    }
    public String getErrorFile(){return filePath;}

}
