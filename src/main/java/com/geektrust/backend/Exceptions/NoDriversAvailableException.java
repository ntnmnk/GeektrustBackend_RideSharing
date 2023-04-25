package com.geektrust.backend.Exceptions;

public class NoDriversAvailableException extends RuntimeException{
    public NoDriversAvailableException()
    {
     super();
    }
    public NoDriversAvailableException(String msg)
    {
     super(msg);
    }

}