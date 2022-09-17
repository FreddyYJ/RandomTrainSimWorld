package com.github.freddyyj.randomtrainsimworld.exception;

import java.io.IOException;

/**
 * an {@code IOException} that has file name
 * <p>
 *     This exception extends {@link IOException}, but has file name that error occur.
 * </p>
 * @author FreddyYJ_
 * @version 0.2.0
 */
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
