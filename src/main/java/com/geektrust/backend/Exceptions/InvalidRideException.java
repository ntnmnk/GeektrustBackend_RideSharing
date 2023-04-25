package com.geektrust.backend.Exceptions;

public class InvalidRideException extends RuntimeException {
    public InvalidRideException()
    {
     super();
    }
    public InvalidRideException(String msg)
    {
     super(msg);
    }

}
