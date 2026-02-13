package com.company.exception;

import org.springframework.stereotype.Component;


public class ResourceNotFound extends RuntimeException
{
   public ResourceNotFound(String msg)
    {
        super(msg);
    }
}
