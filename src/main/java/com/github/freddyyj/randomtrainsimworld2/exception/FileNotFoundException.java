package com.github.freddyyj.randomtrainsimworld2.exception;

/**
 * throws if file is not exist
 * <p>
 *     Overrides {@link java.io.FileNotFoundException}, but has additional argument which file occurs error.
 * </p>
 * @author FreddyYJ_
 * @version 0.2.0
 */
public class FileNotFoundException extends java.io.FileNotFoundException {
    private String filePath;

    /**
     * Constructor with file name
     * @param file File name that occurs error
     */
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
