package com.github.freddyyj.randomtrainsimworld2.exception;

import java.io.IOException;

public class FileWriteException extends IOException {
    private String filePath;

    public FileWriteException(String file) {
        super();
        filePath=file;
    }

    public FileWriteException(String message,String file) {
        super(message);
        filePath=file;
    }

    public FileWriteException(String message, Throwable cause,String file) {
        super(message, cause);
        filePath=file;
    }

    public FileWriteException(Throwable cause,String file) {
        super(cause);
        filePath=file;
    }
    public String getErrorFile(){return filePath;}
}
