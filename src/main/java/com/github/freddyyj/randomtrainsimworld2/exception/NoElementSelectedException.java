package com.github.freddyyj.randomtrainsimworld2.exception;

/**
 * Throws if no elements are selected when random picking.
 * @author FreddyYJ_
 */
public class NoElementSelectedException extends RuntimeException{
    /**
     * exception message
     */
    String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    /**
     * exception caused by
     */
    Throwable cause;
    public NoElementSelectedException(String message, Throwable cause){
        this.message=message;
        this.cause=cause;
    }
    public NoElementSelectedException(String message){
        this.message=message;
    }
    public NoElementSelectedException(Throwable cause){
        this("no element selected",cause);
    }
    public NoElementSelectedException(){
        this("no element selected");
    }
}
