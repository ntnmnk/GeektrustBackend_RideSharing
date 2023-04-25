package com.geektrust.backend.Exceptions;

public class CommandNotFoundException extends RuntimeException {

    public CommandNotFoundException()
    {
     super();
    }
    public CommandNotFoundException(String msg)
    {
     super(msg);
    }


    
}
