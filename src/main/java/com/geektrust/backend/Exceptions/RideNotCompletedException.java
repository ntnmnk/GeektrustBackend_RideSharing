package com.geektrust.backend.Exceptions;

public class RideNotCompletedException extends RuntimeException{
    public RideNotCompletedException()
    {
     super();
    }
    public RideNotCompletedException(String msg)
    {
     super(msg);
    }
}
