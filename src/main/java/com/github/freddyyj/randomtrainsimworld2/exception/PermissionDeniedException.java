package com.github.freddyyj.randomtrainsimworld2.exception;

public class PermissionDeniedException extends SecurityException{
    private String fileName;
    public PermissionDeniedException(String file) {
        super();
        fileName=file;
    }

    public PermissionDeniedException(String s,String file) {
        super(s);
        fileName=file;
    }

    public PermissionDeniedException(String message, Throwable cause,String file) {
        super(message, cause);
        fileName=file;
    }

    public PermissionDeniedException(Throwable cause,String file) {
        super(cause);
        fileName=file;
    }
    public String getErrorFile(){return fileName;}
}
